<template>
  <el-form :model="articleData">
      <!-- 자유게시판 글 카테고리를 설정해서 사용 -->
      <el-form-item class="create-category" :span="4">
        <el-select v-model="category" placeholder="글 분류">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="create-title" :span="24">
        <el-input
          v-model="articleData.title"
          placeholder="제목을 입력하세요"
          maxlength="30"
          show-word-limit
        />
        <el-divider class="divider-line" />
      </el-form-item>
      <!-- review board end -->
      <el-form-item class="create-content">
        <el-input
          v-model="articleData.content"
          type="textarea"
          :autosize="{ minRows: 10, maxRows: 50 }"
          placeholder="내용을 입력하세요"
          style=""
        />
      </el-form-item>
      <el-form-item>
        <el-button color="#2c2cff" @click="onSubmit" round>{{ action }}</el-button>
        <el-button round>취소</el-button>
      </el-form-item>
  </el-form>
</template>

<script setup>
import { ref } from "vue";
import { useStore } from "vuex";

const props = defineProps({
  articleData: Object,
  action: String,
})

const store = useStore()
const userPk = JSON.parse(sessionStorage.User).id

const category = ref(props.articleData.type);

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

const articleData = ref({
  id: props.articleData.id,
  type: category.value,
  userId: userPk,
  title: props.articleData.title,
  content: props.articleData.content,
})

const onSubmit = () => {
  if (props.action === "글 등록") {
    store.dispatch('createArticle', articleData.value)
  } else if (props.action == "글 수정") {
    store.dispatch('editArticle', articleData.value)
  }
};

</script>

<style></style>
