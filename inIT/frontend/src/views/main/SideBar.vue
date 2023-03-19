<template>
  <div class="side-bar container-box">
    <div class="menu-profile">
      <el-avatar
        v-if="imgUrl == null"
        class="side-bar-avatar"
        :size="80"
        :src="defaultImg"
      />
      <el-avatar v-else class="side-bar-avatar" :size="80" :src="imgUrl" />
      <div class="menu-profile-info">
        <div class="menu-profile-name">
          {{ userNick }}
        </div>
        <div class="menu-profile-rank">lv.{{ userGrade }}</div>
        <div class="logout-btn">
          <el-button
            v-if="isLogin"
            size="small"
            type="warning"
            plane
            @click="logout"
            >로그아웃</el-button
          >
        </div>
      </div>
    </div>
    <el-menu
      active-text-color="#ffd04b"
      class="el-menu-vertical-demo side-menu"
      text-color="#fff"
      @open="handleOpen"
      @close="handleClose"
    >
      <el-menu-item index="1" class="menu-item" @click="linkTo('Home')">
        <span class="menu-title">홈</span>
      </el-menu-item>
      <el-menu-item index="3" class="menu-item" @click="linkTo('ReviewNote')">
        <span class="menu-title">오답노트</span>
      </el-menu-item>
      <el-menu-item index="2" class="menu-item" @click="linkTo('MyReportList')">
        <span class="menu-title">면접 일지</span>
      </el-menu-item>
      <el-menu-item index="3" class="menu-item" @click="linkTo('Profile')">
        <span class="menu-title">나의 프로필</span>
      </el-menu-item>
      <el-sub-menu index="4" class="menu-item">
        <template #title>
          <span class="menu-title menu-community">커뮤니티</span>
        </template>
        <el-menu-item-group>
          <el-menu-item
            index="3-1"
            class="menu-sub-title"
            @click="linkTo('GeneralArticleList')"
            >자유 게시판</el-menu-item
          >
          <el-menu-item
            index="3-2"
            class="menu-sub-title"
            @click="linkTo('ReviewArticleList')"
            >면접 후기 게시판</el-menu-item
          >
          <el-menu-item
            index="3-3"
            class="menu-sub-title"
            @click="linkTo('InfoArticleList')"
            >정보 게시판</el-menu-item
          >
          <el-menu-item
            index="3-4"
            class="menu-sub-title"
            @click="linkTo('FeedbackArticleList')"
            >피드백 게시판</el-menu-item
          >
        </el-menu-item-group>
      </el-sub-menu>
    </el-menu>
    <div class="side-bar-enter">
      <el-button
        class="enter-btn"
        color="#fff"
        @click="linkTo('InterviewSettings')"
        >면접 연습하기</el-button
      >
    </div>
    <!-- enter buttn end -->
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const router = useRouter();
const defaultImg =
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

const isLogin = store.getters.isLogin;

const logout = () => {
  store.dispatch("doLogout");
  alert("로그아웃 되었습니다");
  router.push({ path: "/" });
};

const user = JSON.parse(sessionStorage.getItem("User"));
// const user = store.getters.userInfo;
let userNick = "";
let userGrade = 1;
let imgUrl = null;

if (user) {
  userNick = user.nick;
  userGrade = user.grade;
  imgUrl = user.imgUrl;
} else {
}

// const userNick = computed(store.getters.userInfo).nick;
// const userGrade = computed(store.getters.userInfo).grade;
// const imgUrl = computed(store.getters.userInfo).imgUrl;

// 접근 제한은 잘 걸렸는데, 오히려 사이드바에 정보가 제대로 출력되지 않는 오류가 생김 >> 원래대로

const linkTo = (name) => router.push({ name: name });

onMounted(() => {
  const user = JSON.parse(sessionStorage.getItem("User"));
  if (user) {
    store.dispatch("readLoginInfo", user);
  } else {
    alert("잘못된 접근입니다");
    router.push({ name: "Login" });
  }
});
</script>

<style></style>
