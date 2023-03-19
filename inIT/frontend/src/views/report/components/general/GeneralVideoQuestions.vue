<template>
    <el-row class="recorded-video">
      <el-col>
        <div class="video-container">
        {{ questions[currentQ].videoUrl }}
        </div>
      </el-col>
      <el-col class="video-question">
        <span class="q-num">Q{{ currentQ }}. </span>{{ questions[currentQ].question }}
      </el-col>
      <!-- pagination -->
      <el-col>
        <div class="pagination-box">
          <el-pagination layout="prev, pager, next" :total="totalQ" @current-change="setQuestion" />
        </div>
      </el-col>
      <el-col>
        <GeneralTotalQuestionList :questions="questions"/>
      </el-col>
    </el-row>
</template>

<script setup>
import { ref } from "vue"
import GeneralTotalQuestionList from "./GeneralTotalQuestionList.vue"

const props = defineProps({
  questions: Array,
})

const totalQ = ref(props.questions.length * 10)
const currentQ = ref(1)

const setQuestion = (page) => {
  currentQ.value = page - 1
}


</script>

<style>
</style>