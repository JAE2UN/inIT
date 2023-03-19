<template>
  <div class="common-layout">
    <el-container>
      <!-- <el-header class="d-flex header-logo">
        <img src="src/assets/images/inIT.png" alt="logo" />
      </el-header> -->
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
          <SideBar></SideBar>
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <el-row class="profile-two-sections">
            <el-col :span="11" class="profile-section">
              <el-scrollbar>
                <UserDetail></UserDetail>
              </el-scrollbar>
            </el-col>
            <el-col :offset="1" :span="12" class="profile-section">
              <el-scrollbar>
                <MyQList></MyQList>
              </el-scrollbar>
            </el-col>
          </el-row>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>
<script setup>
import SideBar from "../main/SideBar.vue";
import UserDetail from "./UserDetail.vue";
import MyQList from "./MyQList.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { onMounted } from "vue";

const store = useStore();
const router = useRouter();
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>

<style></style>
