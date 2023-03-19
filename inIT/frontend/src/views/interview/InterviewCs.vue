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
              <el-scrollbar class="inner-scroll">
                <el-row>
                  <div class="question-box">
                    <div class="interview-question">
                      Q1.  본인의 단점 세 가지만 이야기해주세요
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
                     
                    <el-button class="exit-btn" @click="exitDialogVisible = true">연습 종료</el-button>
                  </el-col>
                </el-row>
              </el-scrollbar>
            </div>
          </div>
          <!-- exit dialog -->
          <el-dialog v-model="exitDialogVisible" custom-class="exit-dialog">
            <el-row class="dialog-row">
              <!-- <el-col class="dialog-box"> -->
                <el-col class="thumbnail">
                  <div>
                    떰브네일
                  </div>
                </el-col>
                <el-col class="content">
                  <span class="first">연습이 종료되었어요!</span>
                  <span class="second">지금 리뷰하시겠어요?</span>
                </el-col>
                <el-col class="btn-box">
                  <el-button class="now-btn" @click="linkTo('InterviewReviewCs', 3)">지금 리뷰하기</el-button>
                  <el-button class="next-btn" @click="linkTo('MyReportList')">다음에 리뷰하기</el-button>
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
import { ref } from "vue";
import { useRouter } from "vue-router";
import Stopwatch from "./components/Stopwatch.vue"

const exitDialogVisible = ref(false)


const router = useRouter();
const linkTo = (name, reviewPk) => router.push({ name: name, params: { reviewPk: reviewPk } });
</script>

<style>
</style>
