<template>
  <el-col :offset="18" :span="6" class="q-btn-container">
    <el-button class="more-btn" @click="TotalQlistDialogVisible = true">
      면접 전체보기
    </el-button>
  </el-col>

  <!-- My Question List Modal Dialog -->
  <el-dialog v-model="TotalQlistDialogVisible" width="40%">
    <el-scrollbar>
      <div class="general-modal-container">
        <el-row class="modal-title">
          전체 면접 질문
        </el-row>
        <el-row class="total-category">
          <el-col 
            class="total-modal-item"
            v-for="question in questions"
            :key="question"
          >
            <span>
              <i class="fas fa-angle-right qlist-arrow"></i> {{ question.question }}
            </span>
          </el-col>
        </el-row>
      </div>
    </el-scrollbar>
  </el-dialog>    

</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  questions: Array,
})


const TotalQlistDialogVisible = ref(false)

</script>

<style>
  
</style>