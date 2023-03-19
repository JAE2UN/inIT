<template>
  <el-col :offset="20" :span="4" class="q-btn-container">
    <el-button class="more-btn" @click="TotalQlistDialogVisible = true">
      질문 전체보기
    </el-button>
  </el-col>

  <!-- My Question List Modal Dialog -->
  <el-dialog v-model="TotalQlistDialogVisible" width="40%">
    <el-scrollbar>
      <div class="total-modal-container">
        <el-row class="modal-title">
          전체 면접 질문
        </el-row>
        <el-row class="total-category">
          <el-col 
            class="total-modal-item"
            v-for="question in questionList"
            :key="question"
          >
            <el-row v-if="question.flag == 1">
              <span>
                <i class="fas fa-angle-right qlist-arrow"></i> {{ question.quest }}
              </span>
            </el-row>
          </el-col>
        </el-row>
      </div>
    </el-scrollbar>
  </el-dialog>    

</template>

<script setup>
import { ref, computed } from 'vue'
import { useStore } from "vuex";

const store = useStore();

// const props = defineProps({
//   questions: Array,
// })

const questionList = computed(() => store.getters.questionList)
const TotalQlistDialogVisible = ref(false)

</script>

<style>
  
</style>