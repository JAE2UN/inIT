<template>
  <el-col :span="12" class="review-video">
    <div class="left-section d-flex">
      <!-- videos & questionList -->
      <el-row>
        <el-col class="question-video">
          <div class="video-container">
            <!-- {{ questionList[currentQ].videoUrl }} -->
          </div>
        </el-col>
        <div v-if="questionList[currentQ].flag == 1">
          <el-col class="video-question">
            <span class="q-num">Q{{ currentQ + 1 }}. </span>
            {{ questionList[currentQ].quest }}
          </el-col>
        </div>
        <!-- pagination -->
        <el-col>
          <div class="pagination-box">
            <el-pagination
              layout="prev, pager, next"
              :total="totalQ"
              @current-change="setQuestion"
            />
          </div>
        </el-col>
        <el-col class="total-q">
          <GeneralTotalQuestionList :questions="questionList" />
        </el-col>
        <el-col :span="3" class="comment-title"> 총평 </el-col>
        <el-col :span="1" class="interview-title-lh">
          <span class="edit-btn" @click="switchIsEditing">
            <i class="fas fa-pen"></i>
          </span>
        </el-col>
        <el-col class="comment">
          <pre v-if="!isEditing" class="fixed-comment">{{ comment }}</pre>
          <div v-if="isEditing" class="comment-textarea">
            <el-input
              v-model="comment"
              :rows="3"
              type="textarea"
              placeholder="총평을 입력하세요."
              @change="switchIsEditing"
            />
          </div>
        </el-col>
      </el-row>
    </div>
  </el-col>
</template>

<script setup>
import { computed, ref } from "vue";
import store from "../../../store";
import GeneralTotalQuestionList from "../components/GeneralTotalQuestionList.vue";

const props = defineProps({
  questions: Array,
  comment: String,
});

// const props = defineProps({
//   questions: Array,
//   comment: String,
// });

// const totalQ = ref(props.questions.length * 10)
const totalQ = computed(() => store.getters.questionCount);
const questionList = computed(() => store.getters.questionList);
const comment = computed(() => store.getters.allCmt);
const currentQ = ref(0);
const setQuestion = (page) => {
  currentQ.value = page - 1;
};

const isEditing = ref(true);
const switchIsEditing = () => {
  isEditing.value = !isEditing.value;
};
</script>
<style></style>
