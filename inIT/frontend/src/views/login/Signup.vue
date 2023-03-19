<template>
  <el-row class="signup-container">
    <el-col :offset="6" :span="12">
      <img class="signup-logo" src="../../assets/images/inIT.png" alt="logo" />
      <p>서비스를 이용하기 위해</p>
      <p><b>정보 입력</b>과 <b>약관 동의</b>를 진행해주세요</p>
      <div class="signup-box">
        <el-row class="userid-box d-flex">
          <el-col :span="20">
            <el-input
              v-model="email"
              placeholder="이메일을 입력해주세요 (your@email.com)"
              type="text"
              @change="isValidID = false"
            />
          </el-col>
          <el-col :span="3" :offset="1">
            <el-button @click="idCheck">중복확인</el-button>
          </el-col>
        </el-row>

        <el-input
          v-model="password"
          placeholder="비밀번호를 입력해주세요 (숫자, 문자 포함 8글자 이상)"
          type="password"
          show-password
        />
        <el-row class="nickname-box d-flex">
          <el-col :span="20">
            <el-input
              v-model="nickname"
              placeholder="닉네임을 입력해주세요 (3~10 글자)"
              type="text"
              minlength="3"
              maxlength="10"
              @change="isValidNick = false"
          /></el-col>
          <el-col :span="3" :offset="1">
            <el-button @click="nickCheck">중복확인</el-button>
          </el-col>
        </el-row>

        <div
          class="agree-text"
          style="
            background-color: white;
            width: 100%;
            border: 1px gray;
            margin-top: 20px;
            margin-inline: auto;
          "
        >
          <el-scrollbar
            max-height="100px"
            style="text-align: left; font-size: 0.5rem; padding-inline: 5px"
          >
            <pre>
본 inIT 이용약관(이하 ‘본 약관’이라 함)은 inIT_a703(이하 ‘inIT’이라 함)이
본 서비스를 이용하는 고객(이하 ‘고객’이라 함)에게 제공하는 모든 서비스(이하 ‘본 서비스’라 함)의 이용에 관한 조건에 대해 정합니다.

<b>1. 개인정보의 수집 및 이용</b>
본 서비스는 가입하여야만 이용할 수 있습니다.
inIT은 사용자를 식별하기 위해 필요한 최소한의 정보를 필수 사항으로 수집하고 있습니다.
1.1.수집항목: 이메일, 비밀번호, 닉네임
1.2.수집목적: 개인 식별
1.3.보유기간: 회원탈퇴시까지

<b>2. 약관 동의</b>
2.1.고객은 본 약관에 따라 본 서비스를 이용해야 합니다.
고객은 본 약관에 대해 동의를 했을 경우에 한하여 본 서비스를 이용할 수 있습니다.
2.2.고객이 미성년자일 경우에는 친권자 등 법정대리인의 사전 동의를 얻은 후 본 서비스를 이용하십시오.
또한 고객이 본 서비스를 사업자를 위해 이용할 경우에는 당해 사업자 역시 본 약관에 동의한 후, 본 서비스를 이용하십시오.
2.3.본 서비스에 적용되는 개별 이용약관이 존재할 경우, 고객은 본 약관 외에 개별 이용약관의 규정에 따라 본 서비스를 이용해야 합니다.

<b>3. 약관 변경</b>
inIT이 필요하다고 판단할 경우, inIT은 본 서비스 목적의 범위 내에서 본 약관을 변경할 수 있습니다.
이때 inIT은 본 약관의 변경본의 내용과 변경의 효력일을 본 서비스 또는 inIT의 웹사이트에 게시하거나,
이를 inIT이 정한 방법으로 고객에게 통지하여야 합니다. 
본 약관의 변경본은 그 효력일로부터 효력이 발생합니다. 

<b>4. 계정</b>
4.1.본 서비스 이용을 위해 고객은 특정 정보를 등록하여 계정을 생성해야 할 수 있습니다.
고객은 진실하고 정확하며 완전한 정보를 등록해야 합니다.
4.2.고객이 본 서비스 이용을 위해 인증 정보를 등록할 경우, 이를 부정하게 이용당하지 않도록 본인 책임 하에 엄중하게 관리해야 합니다.
inIT은 등록된 인증 정보를 이용하여 이루어진 일체의 행위를 인증 정보를 등록한 고객 본인의 행위로 간주할 수 있습니다.
4.3.본 서비스에 등록한 고객은 계정을 삭제하고 탈퇴할 수 없습니다.
빠른 시일 내에 고객이 언제든 삭제하고 탈퇴할 수 있도록 변경하고 본 약관의 변경본을 고객들에게 공지할 것입니다.
4.4.inIT은 마지막 접속 시점부터 １년 이상 경과한 계정을 해당 고객에 대한 사전 통지 없이 삭제할 수 있습니다.
4.5.고객이 본 서비스에서 가지는 모든 이용 권한은 이유를 막론하고 계정이 삭제된 시점에 소멸됩니다.
고객의 실수로 계정을 삭제한 경우에도 계정을 복구할 수 없음에 유의하시기 바랍니다.
4.6.본 서비스의 계정은 고객에게 일신전속적으로 귀속됩니다.
고객이 본 서비스에서 가지는 모든 이용권은 제삼자에게 양도, 대여 또는 처분할 수 없으며, 제삼자에게 상속 또는 승계될 수 없습니다.

<b>5. 프라이버시</b>
5.1.inIT은 고객의 프라이버시를 존중합니다.
5.2.inIT은 고객의 프라이버시 정보와 개인정보를 inIT 개인정보 취급방침에 따라 적절하게 취급합니다.
5.3.inIT은 고객으로부터 수집한 모든 정보를 안전하게 관리하기 위해 보안에 최대한 주의를 기울이고 있습니다.

<b>6. 서비스 제공</b>
6.1.고객은 본 서비스를 이용하는 데 있어 필요한 PC나 휴대전화 단말기,
스마트폰 및 기타 통신 기기, 운영 체제, 통신 수단 및 전력 등을 고객의 비용과 책임 하에 준비해야 합니다.
6.2.inIT은 본 서비스의 전부 또는 일부를 고객의 연령 및 본인 확인 여부,
등록정보 유무 등 inIT이 필요하다고 판단한 조건을 만족시키는 고객에 한하여 제공할 수 있습니다.
6.3.inIT은 필요하다고 판단될 경우, 고객에 대한 사전 통지 없이 언제라도 본 서비스의 전부 또는 일부 내용을 변경할 수 있습니다.
6.4.inIT은 다음 각 호의 경우 고객에 대한 사전 통지 없이 언제라도 본 서비스의 전부 또는 일부의 제공을 중지할 수 있습니다.
(1)시스템을 유지보수 또는 수리하는 경우
(2)사고(화재, 정전 등), 천재지변, 전쟁, 폭동, 노동 쟁의 등 불가항력으로 인해 본 서비스를 제공할 수 없는 경우
(3)시스템이 고장 나거나 심각한 부하가 걸리는 경우
(4) 고객 또는 제삼자의 안전을 확보하거나 공공복리를 위한 비상사태의 경우
(5)(1) 부터 (4)까지 명시된 사항 이외에 inIT이 필요하다고 합리적으로 판단하는 경우

<b>7. 제휴 서비스</b>
본 서비스는 inIT 이외의 제삼자가 제공하는 서비스 또는 콘텐츠를 포함할 수 있습니다.
이러한 서비스 및/또는 콘텐츠에 대한 모든 책임은 이를 제공하는 제삼자에게 있습니다.
또한, 이러한 서비스 및 콘텐츠에는 이를 제공하는 제삼자가 정한 이용약관 기타 조건이 적용되는 경우가 있습니다.

<b>8. 광고 게재</b>
inIT은 본 서비스에 inIT 또는 제삼자의 광고를 게재할 수 있습니다.

<b>9. 연락 방법</b>
9.1.본 서비스와 관련해 inIT이 고객에게 연락을 할 때는
본 서비스 내의 적당한 장소에 게시하거나 기타 inIT이 적당하다고 판단하는 방법으로 합니다.
9.2.본 서비스와 관련해 고객이 inIT에 연락을 할 때는
본 서비스 내의 자유 게시판 페이지를 이용하거나 inIT이 지정하는 방법으로 합니다.

이상
 
최종 업데이트 일자：2022년 8월 17일

</pre>
          </el-scrollbar>
        </div>
        <el-checkbox
          class="agree-btn"
          v-model="agree"
          label="inIT 이용약관에 동의합니다"
          size="large"
        />
      </div>
      <el-button class="signup-btn" color="#2c2cff" @click="register"
        >회원가입</el-button
      >
    </el-col>
  </el-row>
</template>

<script setup>
import { ref, onMounted } from "vue";
import http from "@/api/http.js";
import router from "../../router";

const email = ref("");
const password = ref("");
const nickname = ref("");
const agree = ref("");

let isValidID = false;
let isValidPW = false;
let isValidNick = false;

onMounted(() => {
  const user = JSON.parse(sessionStorage.getItem("User"));
  if (user) {
    router.push({ name: "Home" });
  }
});

const idCheck = () => {
  if (email.value.length >= 4) {
    const input = email.value;
    const emailRegex = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

    if (emailRegex.test(input)) {
      const requestURL = "/user/checkEmail/" + input;
      // console.log(requestURL);

      http
        .get(requestURL)
        .then(({ data }) => {
          if (data == "success") {
            isValidID = true;
            alert("사용 가능한 이메일 입니다");
          }
        })
        .catch((err) => {
          isValidID = false;
          alert("이미 사용중인 이메일 입니다");
        });
    } else {
      isValidID = false;
      alert("유효한 이메일을 입력해주세요");
    }
  } else {
    alert("유효한 이메일을 입력해주세요");
  }

  console.log(isValidID);
};

const pwCheck = () => {
  const input = password.value;
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

  if (passwordRegex.test(input)) {
    isValidPW = true;
  } else {
    isValidPW = false;
  }
};

const nickCheck = () => {
  if (nickname.value.length >= 2) {
    const input = nickname.value;

    const requestURL = "/user/checkNick/" + input;
    // console.log(requestURL);

    http
      .get(requestURL)
      .then(({ data }) => {
        if (data == "success") {
          isValidNick = true;
          alert("사용가능한 닉네임 입니다");
        }
      })
      .catch((err) => {
        isValidNick = false;
        alert("이미 사용중인 닉네임 입니다");
      });
  } else {
    alert("유효한 닉네임을 입력해주세요");
  }

  console.log(isValidNick);
};

const register = () => {
  pwCheck();

  if (isValidID && isValidNick && isValidPW && agree) {
    const userInfo = {
      email: email.value,
      pw: password.value,
      nick: nickname.value,
    };

    // console.log(userInfo);
    // console.log(typeof userInfo);
    const u = JSON.stringify(userInfo); // 잡았다

    // console.log(u);
    http
      .post("/user/signup", u)
      .then(({ data }) => {
        if (data == "success") {
          alert("회원가입에 성공했습니다");
          console.log(data);
          router.push({ name: "Login" });
        }
      })
      .catch((err) => {
        alert("회원가입에 실패했습니다");
        console.log(err);
        // router.go();
      });
  } else if (!isValidID) {
    alert("이메일을 확인해주세요");
  } else if (!isValidPW) {
    alert("비밀번호가 유효하지 않습니다");
  } else if (!isValidNick) {
    alert("닉네임을 확인해주세요");
  } else if (!agree) {
    alert("이용약관에 동의해주세요");
  }
};

const movePageTo = (pageName) => {
  router.push({ name: pageName });
};

// email domain helper
</script>

<style></style>
