<template>
  <el-button @click="dialogFormVisible = true" :icon="EditPen" size="small"
    >수정하기</el-button
  >

  <el-dialog
    v-model="dialogFormVisible"
    title="Edit Profile"
    class="edit-profile-dialog"
    width="50%"
  >
    <el-form :model="form" class="edit-profile-form">
      <el-form-item
        label="Nickname"
        :label-width="formLabelWidth"
        class="edit-profile-form-item"
      >
        <el-input
          v-model="userInfo.nick"
          style="width: 70%"
          maxlength="10"
          show-word-limit
          @input="validReset"
        >
          <template #append>
            <el-button @click="nickCheck" size="small">check</el-button>
          </template>
        </el-input>
        <!-- <el-button @click="nickCheck" size="small" style="margin-left: 10px"
          >check</el-button
        > -->
      </el-form-item>
      <el-form-item
        label="StatusMsg"
        :label-width="formLabelWidth"
        class="edit-profile-form-item"
      >
        <el-input
          v-model="userInfo.statusMsg"
          placeholder="한줄 소개 입력"
          style="width: 70%"
        >
          <!-- <template #append>
            <el-button @click="updateStatus">수정</el-button>
          </template> -->
        </el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="confirm">Confirm</el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script setup>
import { ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { EditPen } from "@element-plus/icons-vue";
import http from "@/api/http.js";

const dialogFormVisible = ref(false);
const formLabelWidth = "200px";

const store = useStore();
const router = useRouter();

const user = JSON.parse(sessionStorage.getItem("User"));

const userInfo = ref({
  email: user.email,
  nick: user.nick,
  statusMsg: user.statusMsg,
  imgUrl: user.imgUrl,
  // grade: user.grade,
  // temperature: user.temperature,
});

let isValidNick = false;

const validReset = () => {
  isValidNick = false;
};

const nickCheck = () => {
  // 1차 검증
  const nick = userInfo.value.nick;
  if (nick.length >= 1) {
    const requestURL = "/user/checkNick/" + nick;
    console.log(requestURL);

    http
      .get(requestURL)
      .then(({ data }) => {
        if (data == "success") {
          isValidNick = true;
          alert("사용가능한 닉네임 입니다");
        }
      })
      .catch((err) => {
        if (err.response.data == "fail") {
          isValidNick = false;
          alert("이미 존재하는 닉네임 입니다");
        } else {
          console.log(err);
          alert("에러가 발생했습니다");
        }
      });
  } else {
    isValidNick = false;
    alert("유효한 nick를 입력해주세요");
  }
};

const confirm = () => {
  const nick = userInfo.value.nick;
  const user = JSON.parse(sessionStorage.getItem("User"));
  if (isValidNick || nick == user.nick) {
    updateUserInfo();
  } else {
    alert("닉네임을 확인해주세요");
  }
};

const updateUserInfo = () => {
  const userInf = {
    email: userInfo.value.email,
    nick: userInfo.value.nick,
    statusMsg: userInfo.value.statusMsg,
    imgUrl: userInfo.value.imgUrl,
  };

  const userRequest = JSON.stringify(userInf);

  console.log(userRequest);

  // updateUserInfo 요청
  http.post("/user/updateUserInfo", userRequest).then(({ data }) => {
    console.log(data);
    const userResponse = {
      id: data.id,
      email: data.email,
      nick: data.nick,
      grade: data.grade,
      temperature: data.temperature,
      statusMsg: data.statusMsg,
      imgUrl: data.imgUrl,
    };

    console.log(userResponse);
    store.dispatch("updateUserInfo", userResponse);
    alert("수정 완료");
    router.go();
  });
};

//   const
</script>
