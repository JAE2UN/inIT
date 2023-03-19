<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img
          src="@/assets/images/inIT.png"
          alt="logo"
          class="header-logo"
          @click="movePageTo('Home')"
        />
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar />
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <!-- inner: 하나의 큰 박스 -> 바꾸고 싶다면 .inner 해제 -->
            <div class="inner" style="padding: 50px">
              <el-row class="dashboard-row">
                <el-col class="dashboard-col" :span="12">
                  <div class="dashboard-content line-chart">
                    <line-chart></line-chart>
                  </div>
                </el-col>
                <el-col class="dashboard-col" :span="12">
                  <div class="dashboard-content total-practice">
                    <total-log></total-log>
                  </div>
                </el-col>
              </el-row>
              <el-row class="dashboard-row">
                <el-col class="dashboard-col" :span="12">
                  <div class="dashboard-content pie-chart">
                    <pie-chart></pie-chart>
                  </div>
                </el-col>
                <el-col class="dashboard-col" :span="12">
                  <div class="dashboard-content feedback-log">
                    <feedback-log></feedback-log>
                  </div>
                </el-col>
              </el-row>
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
import LineChart from "./components/LineChart.vue";
import TotalLog from "./components/TotalLog.vue";
import PieChart from "./components/PieChart.vue";
import FeedbackLog from "./components/FeedbackLog.vue";

import { useRouter } from "vue-router";
const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>

<style></style>
