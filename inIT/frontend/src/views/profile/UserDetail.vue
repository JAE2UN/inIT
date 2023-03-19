<template>
  <div class="inner-userdetail">
    <div class="userdetail-header">
      <div class="userdetail-header-img">
        <el-avatar
          :size="150"
          :src="user.imgUrl == null ? defaultImg : user.imgUrl"
        />
        <EditImgDialog></EditImgDialog>
      </div>
      <div class="userdetail-header-userInfo">
        <div class="userdetail-header-name">
          {{ user.nick }}
        </div>
        <div class="userdetail-header-email">( {{ user.email }} )</div>
      </div>
      <el-button
        @click="dialogFormVisible = true"
        :icon="EditPen"
        size="small"
        class="img-editbtn-lvup"
        >등급업 요청</el-button
      >
      <div class="userdetail-header-progress">
        <el-progress
          :text-inside="false"
          :stroke-width="30"
          :percentage="gradeVal"
          :color="customColors"
          >lv.{{ user.grade }}</el-progress
        >
      </div>
      <div class="userdetail-header-progress temperature-bar">
        <el-progress
          :text-inside="false"
          :format="format"
          :stroke-width="10"
          :percentage="user.temperature"
          :color="customColors"
          :show-text="true"
        ></el-progress>
      </div>
    </div>

    <div class="userdetail-introduce">
      <div class="userdetail-introduce-title">한줄소개</div>
      <div class="userdetail-introduce-text">
        {{ user.statusMsg }}
      </div>
    </div>
    <div class="userdetail-footer d-flex">
      <edit-profile-dialog :userInfo="userInfo"></edit-profile-dialog>
    </div>
  </div>
</template>
<script setup>
import EditProfileDialog from "./EditProfileDialog.vue";
import EditImgDialog from "./EditImgDialog.vue";
import { onMounted } from "vue";
import http from "../../api/http";
import { useStore } from "vuex";
const store = useStore();
let user = JSON.parse(sessionStorage.getItem("User"));
const defaultImg =
  "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

const gradeVal = user.grade * 20;

const customColors = [
  { color: "#f56c6c", percentage: 21 },
  { color: "#e6a23c", percentage: 41 },
  { color: "#5cb87a", percentage: 61 },
  { color: "#1989fa", percentage: 81 },
  { color: "#6f7ad3", percentage: 100 },
];

const format = (percentage) => {
  return `${percentage}℃`;
};

onMounted(() => {
  user = JSON.parse(sessionStorage.getItem("User"));
  const requestURL = "/user/detailUserInfo/" + user.email;

  http
    .get(requestURL)
    .then((data) => {
      console.log("유저 정보 갱신 성공");
      console.log(data.data);
      store.dispatch("updateUserInfo", data.data);
    })
    .catch((err) => {
      alert("유저 정보를 갱신하는데 오류가 발생했습니다");
      console.log(err);
    });
});
</script>
<style></style>
