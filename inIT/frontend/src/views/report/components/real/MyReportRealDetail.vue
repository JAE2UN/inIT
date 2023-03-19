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
                      <div class="fixed">
                        {{ reportData.title }}
                      </div>
                    </el-col>
                    <el-col :span="4">
                      <div class="date" v-if="isReportLoad">{{ reportData.createdAt }}</div>
                      <!-- <div class="date" v-if="isReportLoad">{{ reportData.createdAt.substring(2, 10) }}</div> -->
                    </el-col>
                    <el-col :span="14" class="button-box">
                      <!-- @click="linkTo('MyReportList')" -->
                      <el-button class="to-board-btn" @click="linkTo('ReviewArticleCreate')">면접후기 쓰기</el-button>
                      <el-button class="save-btn" @click="linkTo('MyReportRealEdit')">수정</el-button>
                      <el-button class="to-board-btn" @click="deleteMyReportReal">삭제</el-button>
                    </el-col>
                  </el-row>
                  <el-row class="real-report-inner">
                    <RealQuestionList/>
                    <RealReportDetail
                      :star="reportData.star"
                      :allCmt="reportData.allCmt"
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
import RealQuestionList from "@/views/report/components/real/RealQuestionList.vue"
import RealReportDetail from "@/views/report/components/real/RealReportDetail.vue"
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from "vue-router"
import { useStore } from "vuex";

const router = useRouter();
const store = useStore()
const route = useRoute()

const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const isReportLoad = computed(() => store.getters.isReportLoad)

store.dispatch('fetchMyReportReal', reportPk)

const linkTo = (name) => router.push({ name: name, params: { reportPk: reportPk } });

const userPk = JSON.parse(sessionStorage.User).id
const fetchInfo = {
  reportPk: reportPk,
  userPk: userPk,
}

const deleteMyReportReal = () => {
  if (confirm("정말 삭제하시겠습니까?")) {
    store.dispatch("deleteMyReportReal", fetchInfo)    
  }
}


</script>

<style>
</style>