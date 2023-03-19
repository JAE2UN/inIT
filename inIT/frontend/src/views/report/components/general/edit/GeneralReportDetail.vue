<template>
  <el-col :span="12" class="review-video">
    <div class="left-section d-flex">
      <el-row class="expired-row">
        <el-col :span="20" class="expired-title">
          면접 문항
        </el-col>
        <el-col :span="4" class="expired-video">
          <el-button text @click="dialogVisible = true">
            <i class="fas fa-play-circle icon-m"></i> 다시 보기
          </el-button>
        </el-col>
        <el-scrollbar>
          <el-row v-for="check in props.checklistRes?.answerRes">
            <el-col v-if="check.flag == 1">
              {{ check.quest }}
            </el-col>
          </el-row>
        </el-scrollbar>
      </el-row>
      <el-divider/>
      <el-row>
        <el-col :span="3" class="comment-title">
          총평
        </el-col>
        <el-col class="fixed-comment">
          {{ checklistRes?.allCmt }}
        </el-col>          
      </el-row>
    </div>
  </el-col>

  <el-dialog
    v-model="dialogVisible"
    width="40%"
  >
    <el-row class="video-dialog">
      <el-col class="question-video">
        <div class="video-container">
        {{ questions[currentQ].videoUrl }}
        </div>
      </el-col>
      <el-col class="video-question">
        <span class="q-num">Q{{ currentQ + 1 }}. </span>{{ questions[currentQ].question }}
      </el-col>
      <el-col>
        <div class="pagination-box">
          <el-pagination layout="prev, pager, next" :total="totalQ" @current-change="setQuestion" />
        </div>
      </el-col>
    </el-row>
  </el-dialog>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  checklistRes: Object,
})
const generalQs = ref([])

// props.checklistRes?.answerRes?.forEach(answer => {
//   if ( answer.flag == 1) {
//     generalQs.value.push(answer.quest)
//   }
// })

const dialogVisible = ref(false)

const totalQ = ref(generalQs.value.length * 10)
const currentQ = ref(0)
const setQuestion = (page) => {
  currentQ.value = page - 1
}

const isEditing = ref(false)
const switchIsEditing = () => {
  isEditing.value = !isEditing.value
}



</script>
<style>
  
</style>