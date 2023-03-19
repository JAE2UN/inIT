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
          <div class="main-container container-box report-container">
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                  <el-row class="interview-info">
                    <el-col :span="6" class="interview-title">
                      <div class="fixed">
                        {{ reportData.title }}
                      </div>
                      <!-- <div v-if="isEditing" class="edited title-box">
                        <el-input
                          placeholder="면접일지 제목을 입력하세요."
                          v-model="interviewData.title"
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
                    <el-col :span="4" class="report-date">
                      {{ reportData.createdAt }}
                    </el-col>
                    <el-col :span="14" class="interview-save">
                      <el-button class="edit-btn save-btn" @click="linkToEdit()">수정</el-button>
                      <el-button class="back-btn" @click="linkTo('MyReportList')">목록으로</el-button>
                    </el-col>
                  </el-row>
                  <el-row class="cs-report">
                    <CsReportMyQList
                      :checklistRes="reportData.checklistRes"
                      :average="average"
                    />
                    <CsReportDetail
                      :star="reportData.star"
                      :oneCmt="reportData.oneCmt"
                      :tags="reportData.tags"
                    />
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
import CsReportMyQList from "@/views/report/components/cs/CsReportMyQList.vue"
import CsReportDetail from "@/views/report/components/cs/CsReportDetail.vue"

import { ref, computed } from 'vue'
import { useStore } from 'vuex';
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const store = useStore()
const route = useRoute()
const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const average = computed(() => store.getters.average)

store.dispatch('fetchMyReportCs', reportPk)


const linkTo = (name) => router.push({ name: name });
const linkToEdit = () => router.push({ name: "MyReportCsEdit",  params: { reportPk: reportPk } })

</script>

<style>
</style>