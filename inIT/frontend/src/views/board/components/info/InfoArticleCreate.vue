<template>
  <div class="article-create">
    <el-form :model="form">
      <el-form-item class="create-title" :span="24">
        <el-input
          v-model="form.title"
          placeholder="제목을 입력하세요"
          maxlength="30"
          show-word-limit
        />
        <el-divider class="divider-line" />
      </el-form-item>
      <!-- if type, changed to review board -->
      <el-form-item v-if="type">
        <el-select
          v-model="form.interview"
          placeholder="면접을 선택하세요"
          @change="fillTextarea"
        >
          <el-option label="2022.04.04 TOSS" value="1" />
          <el-option label="2022.02.04 삼성" value="2" />
        </el-select>
        <ul v-if="listToggle">
          <li v-for="interview in interviews">
            {{ interview.content }}
          </li>
        </ul>
      </el-form-item>
      <!-- review board end -->
      <el-form-item class="create-content">
        <el-input
          v-model="form.content"
          type="textarea"
          :autosize="{ minRows: 10, maxRows: 50 }"
          placeholder="내용을 입력하세요"
          style=""
        />
      </el-form-item>
      <el-form-item>
        <el-button color="#2c2cff" @click="onSubmit" round>Create</el-button>
        <el-button round>Cancel</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { ref, reactive } from "vue";

const type = ref(false);

// do not use same name with ref
const form = reactive({
  title: "",
  interview: "",
  due: "",
  content: "",
});

const options = [
  {
    value: 1,
    label: "일반",
  },
  {
    value: 2,
    label: "질문",
  },
  {
    value: 3,
    label: "정보",
  },
];

const interviews = [
  {
    key: 1,
    content: "우리은행의 광고를 본 적이 있는가? 기억에 남는 광고는 무엇인가? ",
  },
  {
    key: 2,
    content: "핀테크에 대해 설명하고 우리은행의 핀테크 사업 특징을 설명해보라",
  },
  {
    key: 3,
    content: "인터넷전문은행에 대해 설명해보라",
  },
  {
    key: 4,
    content: "사람들과 갈등한 경험과 해결방법은 무엇인가",
  },
  {
    key: 5,
    content: "우리은행의 광고를 본 적이 있는가? 기억에 남는 광고는 무엇인가? ",
  },
  {
    key: 6,
    content: "핀테크에 대해 설명하고 우리은행의 핀테크 사업 특징을 설명해보라",
  },
  {
    key: 7,
    content: "인터넷전문은행에 대해 설명해보라",
  },
  {
    key: 8,
    content: "사람들과 갈등한 경험과 해결방법은 무엇인가",
  },
];

const listToggle = ref(false);
const fillTextarea = () => {
  listToggle.value = true;
  // textarea에 value를 예쁘게 넣는 방법
};

const onSubmit = () => {
  console.log(form.interview);
};
</script>

<style></style>
