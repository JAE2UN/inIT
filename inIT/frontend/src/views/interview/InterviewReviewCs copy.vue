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
          <div class="main-container container-box interview-cs">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                  <!-- title, date -->
                  <el-row class="interview-info">
                    <el-col :span="9" class="interview-title">
                      <div class="fixed">
                        기술면접 연습
                      </div>
                      <!-- <div v-if="isEditing" class="edited title-box">
                        <el-input
                          placeholder="면접일지 제목을 입력하세요."
                          v-model="questionData.title"
                          @change="switchIsEditing"
                          maxlength="10"
                          show-word-limit
                        >
                        </el-input>
                        <el-divider class="divider-line"/>
                      </div> -->
                    </el-col>
                    <!-- <el-col :span="1" class="interview-title-lh">
                      <span class="edit-btn" @click="switchIsEditing">
                        <i class="fas fa-pen"></i>
                      </span>
                    </el-col> -->
                    <el-col :span="3" class="interview-date interview-title-lh">
                      <!-- {{ interviewData.createdAt }} -->
                    </el-col>
                    <el-col :span="12" class="interview-save">
                      <el-button class="save-btn" @click="linkTo('MyReportList')">저장</el-button>
                    </el-col>
                  </el-row>
                  <el-row class="review-inner">
                    <GeneralReviewLeft :questions="interviewData.questions" :comment="interviewData.comment"/>
                    <CsReviewQList :questions="interviewData.questions"/>
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
import SideBar from "@/views/main/SideBar.vue"
import GeneralReviewLeft from "@/views/interview/components/GeneralReviewLeft.vue"
import CsReviewQList from "@/views/interview/components/CsReviewQList.vue"
import { ref, computed } from 'vue'
import { useStore } from 'vuex';
import { useRouter, useRoute } from "vue-router"


const router = useRouter();
const store = useStore()
const route = useRoute()

const reportPk = route.params.reportPk
const interviewData = computed(() => store.getters.interview)

store.dispatch("InterviewReview", reportPk)
console.log(interviewData.value)
const linkTo = (name) => router.push({ name: name });
</script>

<style>
</style>