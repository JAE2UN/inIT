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
            <div class="inner board-inner">
              <el-scrollbar class="inner-scroll">
                <!-- <article-list/> -->
                <router-view></router-view>
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
import { onMounted } from "vue";
import { useRouter, RouterView } from "vue-router";
const router = useRouter();

// 페이지 이동
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>

<style></style>
