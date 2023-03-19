<template>
  <el-form :model="articleData">
    <el-form-item class="create-title" :span="24">
      <el-input
        v-model="articleData.title"
        placeholder="제목을 입력하세요"
        maxlength="30"
        show-word-limit
      />
      <el-divider class="divider-line" />
    </el-form-item>
    <el-form-item>
      <ul class="review-info">
        <li><i class="fas fa-building"></i> 회사명: {{ reportData.company }}</li>
        <li><i class="fas fa-edit"></i> 면접 정보: {{ reportData.info }}</li>
        <li><i class="far fa-calendar-check"></i> 면접 일자: {{ reportData.realDate }}</li>
      </ul>
    </el-form-item>
    <el-form-item>
      <ul v-for="quest in reportData.realQaRes">
        <li>{{ quest.question }}</li>
      </ul>
    </el-form-item>
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
import { ref, computed, onMounted } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router"


const props = defineProps({
  articleData: Object,
  action: String,
})

const route = useRoute()
const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const isReportLoad = computed(() => store.getters.isReportLoad)
console.log(reportData)
onMounted(() => {
  store.dispatch('fetchMyReportReal', reportPk)
});


const store = useStore()
const userPk = JSON.parse(sessionStorage.User).id

const articleData = ref({
  id: props.articleData.id,
  realreportId: props.articleData.realreportId,
  userId: userPk,
  title: props.articleData.title,
  content: props.articleData.content,
})

const onSubmit = () => {
  if (props.action === "글 등록") {
    store.dispatch('createReviewArticle', articleData.value)
  } else if (props.action == "글 수정") {
    store.dispatch('editReviewArticle', articleData.value)
  }
};

</script>

<style></style>
