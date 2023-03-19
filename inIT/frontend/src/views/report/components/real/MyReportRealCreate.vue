<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container report">
        <el-aside width="220px">
          <side-bar/>
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box cs-report-container">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                <!-- title, date -->
                <el-row class="report-info-real">
                  <el-col :span="6" class="report-title">
                    <div class="edited title-box">
                      <el-input
                        placeholder="면접일지 제목을 입력하세요."
                        v-model="reportData.title"
                        maxlength="10"
                        show-word-limit
                      >
                      </el-input>
                    </div>
                  </el-col>
                  <el-col :span="4" class="report-date">
                    <div class="date">{{ reportData.date }}</div>
                  </el-col>
                  <el-col :span="14" class="button-box">
                    <el-button class="save-btn" @click="submitReal">저장</el-button>
                  </el-col>
                </el-row>
                <el-row class="real-report-inner">


                  <!-- left section -->
                  <el-col :span="12" class="real-list-q">
                    <el-scrollbar>
                      <div class="real-container">
                        <div class="interview-info-input">
                          <el-row>
                            <el-col :span="4">회사 정보</el-col>
                            <el-col :span="12">
                              <el-input 
                                v-model="reportData.company" 
                                placeholder="ex. 삼성SDS"
                                @change="addTagCompany"
                              />
                            </el-col>
                          </el-row>
                          <el-row>
                            <el-col :span="4">면접 종류</el-col>
                            <el-col :span="12">
                              <el-input
                                v-model="reportData.info"
                                placeholder="ex. 1차 인적성"
                                @change="addTagInfo"
                              />
                            </el-col>
                          </el-row>
                          <el-row>
                            <el-col :span="4">면접일자</el-col>
                            <el-col :span="12">
                              <el-date-picker
                                v-model="reportData.realDate"
                                type="date"
                                placeholder="실제 면접 일자"
                                :size="size"
                                value-format="YYYY-MM-DD"
                              />
                            </el-col>
                          </el-row>
                        </div>
                        <el-row class="q-list-title title">
                          면접 질문
                        </el-row>
                        <el-row class="notice" v-if="_.isEmpty(reportData.realQaReq)">
                          '수정 / 추가' 버튼으로 면접 질문을 복기해보세요!
                        </el-row>
                        <el-row class="real-q" v-for="i in 3" v-else>
                          <el-col class="category">{{ category[i] }}</el-col>
                          <el-row class="real-q" v-for="q in findType(reportData.realQaReq, i)">
                          {{ q.question }}
                          </el-row>
                        </el-row>
                        <el-row>
                          <el-col :offset="18" :span="4" class="q-btn-container">
                            <el-button class="more-btn" @click="TotalQlistDialogVisible = true">
                              수정 / 추가
                            </el-button>
                          </el-col>
                        </el-row>
                      </div>
                    </el-scrollbar>
                  </el-col>


                  <!-- right section -->
                  <el-col :span="12" class="real-list-content right-container">
                    <el-row class="real-rate">
                      <el-col class="q-list-title title">
                        총점
                      </el-col>
                      <el-col>
                        <el-rate
                          v-model="reportData.star"
                          :texts="['노력해야지 😭😭', '다음엔 더 😓😓', '괜찮았어 😉😉', '어쩌면 붙을지도 😁😁', '합격한다! 🥰🥰']"
                          allow-half
                          show-text
                        />
                      </el-col>
                    </el-row>
                    <el-row class="report-simple-comment">
                      <el-col :span="3" class="simple-title title">
                        한줄평
                      </el-col>
                      <el-col class="simple-content">
                        <el-input
                          placeholder="한줄평을 입력하세요."
                          v-model="reportData.oneCmt"
                          maxlength="35"
                          show-word-limit
                        >
                        </el-input>
                        <el-divider class="divider-line"/>
                      </el-col>
                    </el-row>
                    <el-row class="real-memo">
                      <el-col class="title">
                        메모
                      </el-col>
                      <el-col class="memo-textarea">
                        <el-input
                          v-model="reportData.allCmt"
                          :rows="3"
                          type="textarea"
                          placeholder="메모를 입력하세요"
                        />
                      </el-col>
                    </el-row>
                    <el-row class="real-tags">
                      <el-col class="title">
                        나만의 태그
                      </el-col>
                      <el-col class="tag-content">
                        <el-tag
                          v-for="tag in tagList"
                          :key="tag"
                          class="mx-1"
                          closable
                          @close="handleClose(tag)"
                        >
                          {{ tag }}
                        </el-tag>
                        <el-input
                          v-if="inputVisible"
                          v-model="inputValue"
                          class="ml-1 w-20"
                          size="small"
                          @keyup.enter="handleInputConfirm"
                        />
                        <el-button v-else class="button-new-tag ml-1" size="small" @click="showInput">
                          + New Tag
                        </el-button>
                      </el-col>
                    </el-row>
                  </el-col>
                </el-row>


                <!-- modal dialog -->
                <el-dialog class="real-modal" v-model="TotalQlistDialogVisible" width="50%">
                  <el-scrollbar>
                    <div class="real-modal-container">
                      <el-row>
                        <el-col>
                          <el-form :model="reportData">
                            <el-form-item
                              class="question-item"
                              v-for="(question, index) in reportData.realQaReq"
                              :key="question.key"
                            >
                              <el-row>
                                <el-col :span="18">
                                  <el-input class="question-input" v-model="question.question" placeholder="면접 질문" />
                                </el-col>
                                <el-col :span="4">
                                  <el-select v-model="question.type" class="m-2" placeholder="질문 종류">
                                    <el-option
                                        v-for="(val, key) in category2"
                                        :label="val"
                                        :value="key"
                                      />
                                  </el-select>
                                </el-col>
                                <el-col :span="2">
                                  <el-button
                                    class="mt-2"
                                    @click.prevent="removequestion(question)"
                                  >
                                    삭제
                                  </el-button>
                                </el-col>
                              </el-row>
                              <el-row>
                                <el-col :span="18">
                                  <el-input class="question-input" v-model="question.answer" placeholder="대답"/>
                                </el-col>
                              </el-row>
                            </el-form-item>
                            <el-form-item class="vertical">
                              <el-button @click="addquestion">새 문항</el-button>
                              <el-button type="primary" @click="TotalQlistDialogVisible = false">수정</el-button>
                            </el-form-item>
                          </el-form>
                        </el-col>
                      </el-row>
                    </div>
                  </el-scrollbar>
                </el-dialog>    

              </el-scrollbar>
            </div>
          </div>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script setup>
import SideBar from "@/views/main/SideBar.vue"
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from "vue-router"
import { useStore } from "vuex";
import * as _ from "lodash";

// router
const router = useRouter();
const route = useRoute()
const reportPk = route.params.reportPk
const linkToArticle = () => router.push({ name: "MyReportRealDetail", params: { reportPk: reportPk } });

// data
const userPk = JSON.parse(sessionStorage.User).id
const reportData = ref({
  allCmt: "",
  company: "",
  info: "",
  oneCmt: "",
  realDate: "",
  realQaReq: [],
  star: 0,
  tags: "",
  title: "",
  userId: userPk
})

// tag
const inputValue = ref('')
const tagList = ref([])
// add tag when company/info added
const addTagCompany = () => {
  tagList.value.push(reportData.value.company)
}
const addTagInfo = () => {
  tagList.value.push(reportData.value.info)
}

const inputVisible = ref(false)

const handleClose = (tag) => {
  tagList.splice(tagList.value.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    tagList.value.push(inputValue.value)
  }
  inputVisible.value = false
}

// dialog: interview questions
const TotalQlistDialogVisible = ref(false)
const category = {
  1: "인적성",
  2: "CS",
  3: "프로젝트",
}

const undef = () => {
  return reportData.realQaReq == "undefined"
}

const findType = (arr, type) => {
  return arr.filter(data => parseInt(data.type / 10) == type)
}

const removequestion = (item) => {
  const index = reportData.value.realQaRes.indexOf(item)
  if (index !== -1) {
    reportData.value.realQaRes.splice(index, 1)
  }
}

const category2 = {
  10: "인적성",
  20: "CS",
  30: "프로젝트",
}
const addquestion = () => {
  reportData.value.realQaReq.push(
    {
    type: 10,
    question: "",
    answer: "",
    }
  )
}


// submit
const store = useStore()

const submitReal = () => {
  let mergeTag = ''
  tagList.value.forEach(tag => {
    mergeTag = mergeTag + "#" + tag 
  })
  reportData.value.tags = mergeTag
  store.dispatch("createMyReportReal", reportData.value)
}
</script>

<style>
</style>