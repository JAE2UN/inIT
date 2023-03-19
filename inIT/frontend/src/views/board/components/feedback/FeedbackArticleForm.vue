<template>
  <el-form :model="articleData">
    <el-form-item>
      <!-- <el-input placeholder="videoUrl을 어떻게넣죠" v-model="articleData.video_url"> -->
      <el-input placeholder="videoUrl을 어떻게넣죠">
      </el-input>
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

console.log(props.articleData)

const articleData = ref({
  id: props.articleData.id,
  userId: userPk,
  title: props.articleData.title,
  content: props.articleData.content,
  reportId: props.articleData.reportId,
  sequence: props.articleData.sequence,
})

const onSubmit = () => {
  if (props.action === "글 등록") {
    store.dispatch('createFeedbackArticle', articleData.value)
  } else if (props.action == "글 수정") {
    store.dispatch('editFeedbackArticle', articleData.value)
  }
};

</script>

<style></style>
