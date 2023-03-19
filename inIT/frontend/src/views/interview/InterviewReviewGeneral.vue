<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo" />
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar />
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box interview-general">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                <!-- title, date -->
                <el-row class="interview-info">
                  <el-col :span="6" class="interview-title">
                    <div class="fixed">
                      일반면접 연습
                    </div>
                  </el-col>
                  <el-col :span="4" class="interview-date interview-title-lh">
                    {{ interviewData.date }}
                  </el-col>
                  <el-col :span="14" class="interview-save">
                    <el-button class="save-btn" @click="submitEval"
                      >저장</el-button
                    >
                  </el-col>
                </el-row>
                <el-row class="review-inner">
                  <!-- left section -->
                  <el-col :span="12" class="review-video">
                    <div class="left-section d-flex">
                      <!-- videos & questionList -->
                      <el-row>
                        <el-col class="question-video">
                          <div v-if="isLoaded">
                            <video class="reviewVideo" :src="videoURL[currentQ]?.url" controls>
                              <!-- <source :src="videoURL[currentQ]?.url" type="video/mp4"> -->
                              해당 브라우저는 video 태그를 지원하지 않습니다.
                            </video>
                            <!-- {{ questionList[currentQ].videoUrl }} -->
                          </div>
                        </el-col>
                        <el-col class="pageQ" v-if="questionList[currentQ].flag == 1">
                          <span class="q-num">Q{{ currentQ + 1 }}. </span>
                          {{ questionList[currentQ].quest }}
                        </el-col>
                        <!-- pagination -->
                        <el-col>
                          <div class="pagination-box">
                            <el-pagination
                              layout="prev, pager, next"
                              :total="totalQ"
                              @current-change="setQuestion"
                            />
                          </div>
                        </el-col>
                        <el-col class="total-q">
                          <GeneralTotalQuestionList :questions="questionList" />
                        </el-col>
                        <el-col :span="3" class="comment-title"> 총평 </el-col>
                        <el-col class="comment">
                          <div class="comment-textarea">
                            <el-input
                              v-model="interviewData.allCmt"
                              :rows="3"
                              type="textarea"
                              placeholder="총평을 입력하세요."
                            />
                          </div>
                        </el-col>
                      </el-row>
                    </div>
                  </el-col>

                  <!-- right section -->
                  <el-col :span="12" class="review-right">
                    <div class="right-container d-flex">
                      <!-- my checklist -->
                      <el-row class="checklist-title"> 나만의 체크리스트 </el-row>
                      <div v-for="i in 3">
                        <el-row class="category">{{ Category[i] }}</el-row>
                        <div v-for="question in questionList">
                          <el-row
                            class="checklist"
                            v-if="
                              question.flag == 2 && Math.floor((question.questNo + 3) / 4) == i
                            "
                          >
                            <el-col :span="16" class="check-content">
                              <span>
                                {{ question.quest }}
                              </span>
                            </el-col>
                            <el-col :span="8" class="rate">
                              <span class="rate">
                                <el-radio-group v-model="question.evalScore" size="small">
                                  <el-radio-button label="1" />
                                  <el-radio-button label="2" />
                                  <el-radio-button label="3" />
                                  <el-radio-button label="4" />
                                  <el-radio-button label="5" />
                                </el-radio-group>
                              </span>
                            </el-col>
                          </el-row>
                        </div>
                      </div>
                    </div>
                  </el-col>

<!-- <GeneralReviewMyChecklist /> -->
                </el-row>
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
import SideBar from "@/views/main/SideBar.vue";
import GeneralTotalQuestionList from "./components/GeneralTotalQuestionList.vue";
import { ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";

const route = useRoute();
const store = useStore();
let keyIdx = route.params.reportPk;

const Category = {
  1: "태도",
  2: "내용",
  3: "기타",
};

const questionList = computed(() => store.getters.questionList);
const interviewData = computed(() => store.getters.interview);
const totalQ = computed(() => store.getters.questionCount);
const comment = computed(() => store.getters.allCmt);
const currentQ = ref(0);
const videoURL = computed(() => store.getters.videos)
const setQuestion = (page) => {
  currentQ.value = page - 1;
};
const isLoaded = computed(() => store.getters.isLoaded)



const submitEval = () => {
  const answers = ref([])
  questionList.value.forEach(element => {
    if (element.flag == 2) {
      let ev = element.evalScore
      ev *= 1
      const score = ref({
        questNo: element.questNo,
        evalScore: ev,
        csScore: 0,
      })
      answers.value.push(score.value)
    }
  });
  keyIdx *= 1
  const evalData = {
    allCmt: interviewData.value.allCmt,
    answerReq: answers.value,
    flag: 2,
    reportId: keyIdx,
    type: 1,
  }
  console.log(evalData)
  store.dispatch("submitGeneralEval", evalData)
}


const fetchInfo = {
  reportPk: keyIdx,
  userPk: JSON.parse(sessionStorage.User).id
}

onMounted(() => {
  store.dispatch("API_Test", keyIdx);
  store.dispatch("webRTC", fetchInfo)
});
</script>

<style></style>
