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
          <div class="main-container container-box">
            <!-- inner: 하나의 큰 박스 -> 바꾸고 싶다면 .inner 해제 -->
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                <!-- 여기에 내용 입력 -->
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
import { RouterView } from "vue-router"
</script>

<style>
</style>