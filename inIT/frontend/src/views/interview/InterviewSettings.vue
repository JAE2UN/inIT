<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar/>
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <el-row class="two-sections">


            <!-- list choice -->
            <el-col :span="11" class="section listSection">
              <el-scrollbar>
                <div class="qListSettings">
                  <div class="qListTitle">질문 리스트 선택</div>
                  <el-tabs
                    v-model="activeName"
                    type="card"
                    class="demo-tabs"
                    @tab-click="handleClick"
                  >
                    <!-- 일반면접 탭 -->
                    <el-tab-pane label="일반" name="general">
                      <div class="pickWarning">
                        <i class="fas fa-exclamation-circle"></i> 문항은 최대 15개를 선택하실 수 있습니다.
                      </div>
                      <el-checkbox-group v-model="checkList">
                        <el-checkbox label="제공되는 질문 리스트 연습하기" class="listChoice" @change="generaListCheckChange"/>
                          <el-input-number label="why" v-model="qNum" :min="serverMin" :max="15" @change="handleChange" :disabled="isGeneralDisabled" />
                        <el-checkbox label="나만의 질문 리스트로 연습하기" class="listChoice" @change="generaMyListCheckChange"/>
                          <el-input-number
                            v-model="myNum"
                            :min="0"
                            :max="15"
                            @change="handleMyChange"
                            :disabled="isGeneralMyListDisabled"
                          />
                      </el-checkbox-group>
                      <div class="qStat">
                        총 <span>{{ myNum + qNum }}</span>문제를 선택하여<br>면접 연습은 약 <span>{{ parseInt(( myNum + qNum ) * 1.25) }}</span>분 소요 예정입니다.
                      </div>
                    </el-tab-pane>
                    <!-- CS면접 탭 -->
                    <el-tab-pane label="CS" name="cs">
                      <el-checkbox-group v-model="CsCheckList">
                        <el-checkbox label="제공되는 질문 리스트 연습하기" :indeterminate="isIndeterminate" class="listChoice" @change="csListCheckChange"/>
                          <el-input-number
                            v-model="csNum"
                            :min="1"
                            :max="5"
                            @change="handleMyChange"
                            :disabled="isCsDisabled"
                          />                        
                          <el-checkbox-group v-model="subCheckList" id="cs-checkbox" :disabled="isCsDisabled">
                            <el-checkbox v-for="(type, sub) in allSubCheck" :key="sub" :label="sub" />
                          </el-checkbox-group>                
                        <el-checkbox label="나만의 질문 리스트로 연습하기" class="listChoice" @change="csMyListCheckChange" />
                          <el-input-number v-model="csMyNum" :min="0" :max="5" @change="handleMyChange" :disabled="isCsMyListDisabled"/>
                      </el-checkbox-group>                
                      <div class="qStat">
                        총 <span>{{ (subCheckList.length * csNum) + csMyNum }}</span>문제를 선택하여<br>면접 연습은 약 <span>{{ parseInt(((subCheckList.length * csNum) + csMyNum ) * 0.9) }}</span>분 소요 예정입니다.
                      </div>
                    </el-tab-pane>
                  </el-tabs>
                </div>
              </el-scrollbar>
            </el-col>


            <!-- video settings -->
            <el-col :span="12" :offset="1" class="section videoSection">
              <el-scrollbar>
                <div class="videoSettings">
                  <el-row class="test-video">
                    <div class="guide">
                      <img src="@/assets/images/example2.png" alt="example">
                      <!-- <img src="@/assets/images/guideline.png" alt="quideling"> -->
                      </div>
                  </el-row>
                  <el-row class="tutorial">
                    1. 입장하면 바로 녹화가 시작되고, 상단에 질문이 나옵니다.
                  </el-row>
                  <el-row class="tutorial">
                    2. 답변을 마쳤다면 '다음으로'를 클릭해 다음 문항으로 넘어갑니다.
                  </el-row>
                  <el-row class="tutorial">
                    3. 면접이 모두 끝났다면 녹화된 영상과 함께 면접 연습을 평가해보세요!
                  </el-row>
                </div>
                <!-- enter btn -->
                <div class="enter-box">
                  <!-- <router-link to="/interview/general"> -->
                    <el-button class="enter-btn" color="#2c2cff" @click="submitSettings()">
                      입장하기
                    </el-button>
                  <!-- </router-link> -->
                </div>
              </el-scrollbar>
            </el-col>
          </el-row>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import SideBar from "@/views/main/SideBar.vue"
import { useRouter } from "vue-router";
import { useStore } from "vuex";
// import { joinSession } from './ovjs';


// general tab choice option
const qNum = ref(0)
const myNum = ref(0)
const serverMin = ref(0)
const handleChange = () => {
  if (myNum.value >= 15 - qNum.value) {
    myNum.value = 15 - qNum.value
  }
}
const handleMyChange = () => {
  if (qNum.value >= 15 - myNum.value) {
    qNum.value = 15 - myNum.value
  }
}

const choiceNum = ref([])
const choiceQuest = ref([])

const isGeneralDisabled = ref(true)
const isGeneralMyListDisabled = ref(true)
const isIndeterminate = ref(false)
const checkList = ref([])

const generaListCheckChange = (val) => {
  if ( isGeneralDisabled ) {
    serverMin.value = 3
    qNum.value = 3
  } 
  isGeneralDisabled.value = !val
}

const generaMyListCheckChange = (val) => {
  isGeneralMyListDisabled.value = !val
  if (!isGeneralMyListDisabled.value) {
    myNum.value = 1
  }
}


// cs tab choice option
const csNum = ref(0)
const csMyNum = ref(0)
const isCsDisabled = ref(true)
const isCsMyListDisabled = ref(true)
const CsCheckList = ref([])
const subCheckList = ref([])
const allSubCheck = {'운영체제': 21, '웹': 22, '데이터베이스': 23, '네트워크': 24, '알고리즘': 25, '언어': 26, '기타': 29}

const csListCheckChange = (val) => {
  subCheckList.value = val ? Object.keys(allSubCheck) : []
  isCsDisabled.value = !val
  isIndeterminate.value = false
}

const csMyListCheckChange = (val) => {
  isCsMyListDisabled.value = !val
  if (!isCsMyListDisabled.value) {
    csMyNum.value = 1
  }
}


// submit
const activeName = ref('general')
const userPk = JSON.parse(sessionStorage.User).id
const store = useStore()


const submitSettings = () => {
  if (activeName.value == "general") {
    // joinSession()
    const choiceNum = ref([qNum.value, myNum.value])
    const choiced1 = ref(0)
    const choiced2 = ref(0)
    if (!isGeneralDisabled.value) {
      choiced1.value = 1
    }
    if (!isGeneralMyListDisabled.value) {
      choiced2.value = 1
    }
    const choiceQuest = ref([choiced1.value, choiced2.value])

    const fetchInfo = {
      choiceNum: choiceNum.value,
      choiceQuest: choiceQuest.value,
      type: 1,
      userId: userPk,
    }
    store.dispatch("InterviewSettingsGeneral", fetchInfo)
  } else if (activeName.value == "cs" ) {
    const choiceNum2 = ref([csNum.value, csMyNum.value])
    const submitSubChecked = ref([])

    if (!isCsDisabled.value && !!subCheckList.value) {
      subCheckList.value.forEach(sub => {
        submitSubChecked.value.push(allSubCheck[sub])
      })
    }
    if (!isCsMyListDisabled.value) {
      submitSubChecked.value.push(20)
    }

    const fetchInfo = {
      choiceNum: choiceNum2.value,
      choiceQuest: submitSubChecked.value,
      type: 2,
      userId: userPk,
    }
    console.log(fetchInfo)
    store.dispatch("InterviewSettingsCs", fetchInfo)
  }
}

</script>

<style>
</style>
