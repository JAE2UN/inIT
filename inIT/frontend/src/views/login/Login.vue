<template>
  <el-row class="login-row">
    <el-col :span="9"> </el-col>
    <el-col :span="6" class="login-container d-flex">
      <img class="login-logo" src="../../assets/images/inIT.png" alt="logo" />
      <div class="login-box">
        <el-input
          v-model="email"
          placeholder="아이디"
          @keyup.enter.prevent="login"
        >
          <template #prefix>
            <i class="far fa-user login-icon"></i>
          </template>
        </el-input>
        <el-input
          v-model="pw"
          type="password"
          show-password
          placeholder="비밀번호"
          @keyup.enter.prevent="login"
        >
          <template #prefix>
            <i class="fa fa-lock login-icon"></i>
          </template>
        </el-input>
      </div>
      <el-button class="login-btn" color="#2c2cff" @click="login">
        로그인
      </el-button>
      <el-button class="signup-btn" color="dde6f4" @click="linkTo('SignUp')">
        회원가입
      </el-button>
    </el-col>
  </el-row>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import http from "@/api/http.js";

const store = useStore();

const router = useRouter();
const email = ref("");
const pw = ref("");

onMounted(() => {
  const user = JSON.parse(sessionStorage.getItem("User"));
  if (user) {
    router.push({ name: "Home" });
  }
});

const login = () => {
  // 임시 로그인 패스
  // alert("어서옵쇼");
  // linkTo("Dashboard");
  // router.push({ name: "Dashboard" });

  // 실제 로그인 처리
  const u = JSON.stringify({
    email: email.value,
    pw: pw.value,
  });
  http.post("/user/login", u).then(({ data }) => {
    const key = Object.keys(data)[0];
    const user = data[key];
    if (key == 2) {
      // 정상 로그인
      store.dispatch("doLogin", user);
      alert(user.nick + " 님 환영합니다");
      router.push({ name: "Home" });
    } else if (key == 1) {
      // 비밀번호 불일치
      alert("비밀번호가 일치하지 않습니다");
      router.go();
    } else if (key == 0) {
      // 아이디 불일치
      alert("회원정보가 존재하지 않습니다");
      router.go();
    } else {
      alert("오류가 발생했습니다");
      console.log(data);
      router.go();
    }
  });
};
const linkTo = (name) => router.push({ name: name });
</script>

<style></style>
