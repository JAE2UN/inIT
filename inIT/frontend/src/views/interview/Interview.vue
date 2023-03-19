<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <!-- <el-aside width="220px">
          <side-bar/>
        </el-aside> -->
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <!-- interview view -->
            <div class="inner interview">
              <!-- v-if JoinSession -->
              <el-scrollbar class="inner-scroll">
                <el-row>
                  <div class="question-box">
                    <div class="interview-question">
                      이 곳에 문제가 보여집니다.
                    </div>
                  </div>
                </el-row>
                <el-row>
                  <div class="interviewing-video">
                    송출화면
                    <stopwatch/>
                  </div>
                </el-row>
                <!-- v-if -->
                <el-row class="interview-check">
                  <el-col class="btn-box">
                  
                    <el-button class="next-btn">v-if !recording && !start</el-button>
                    <el-button class="next-btn">v-if !recording && start</el-button>
                    <el-button class="next-btn">v-if recording && start</el-button>
                     
                    <el-button class="exit-btn" @click="exitDialogVisible = true">연습 종료</el-button>
                  </el-col>
                </el-row>
              </el-scrollbar>
              

              <!-- v-if startRecording -->
              <el-scrollbar class="inner-scroll" v-if="false">
                <el-row>
                  <div class="question-box">
                    <div class="interview-question">
                      v-if recording Q1. // v-if !recording 다음 문제를 시작하려면 스페이스바를 누르세요.
                    </div>
                  </div>
                </el-row>
                <el-row>
                  <div class="interviewing-video">
                    송출화면
                    <stopwatch/>
                  </div>
                </el-row>
                <!-- v-if -->
                <el-row class="interview-check">
                  <el-col class="btn-box">
                    <el-button class="next-btn">다음 문항으로</el-button>
                    <!-- <el-button class="exit-btn" @click="exitDialogVisible = true">연습 종료</el-button> -->
                  </el-col>
                </el-row>
              </el-scrollbar>
            </div>
          </div>
          <!-- exit dialog -->
          <el-dialog v-model="isVisible" custom-class="exit-dialog">
            <el-row class="dialog-row">
              <!-- <el-col class="dialog-box"> -->
                <el-col class="thumbnail">
                  <div>
                    떰브네일
                  </div>
                </el-col>
                <el-col class="content">
                  <span class="first">연습이 종료되었어요!</span>
                </el-col>
                <el-col class="btn-box">
                  <el-button class="now-btn" @click="linkTo('InterviewReviewGeneral', 3)">리뷰하러 가기</el-button>
                </el-col>
                <el-col>
                  <template #footer>
                    <span class="dialog-footer">
                      <el-button @click="exitDialogVisible = false">Cancel</el-button>
                    </span>
                  </template>
                </el-col>
              <!-- </el-col> -->
            </el-row>
          </el-dialog>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router"
import Stopwatch from "./components/Stopwatch.vue"

// dialog visible
const store = useStore()
const exitDialogVisible = ref(false)
const isFinished = computed(() => store.getters.isFinished)
const isVisible = ref(exitDialogVisible.value || isFinished.value)

const questionData = computed(() => store.getters.question)
const questionNo = ref(1)
const start = ref(false)
const recording = ref(false)

const route = useRoute()
const reportPk = route.params.reportPk
const startInterview = () => {
  const fetchInfo = ref({
    nextNo: questionNo,
    reportPk: reportPk,
  })
  start.value = true
  // recordingStart()
  store.dispatch("InterviewGeneral", fetchInfo)
  recording.value = !recording.value
  questionNo.value += 1
}

const stopInterview = () => {
  // recordingStop()
  recording.value = !recording.value
}


const router = useRouter();
const linkTo = (name, reviewPk) => router.push({ name: name, params: { reviewPk: reviewPk } });
</script>

<style>
</style>
