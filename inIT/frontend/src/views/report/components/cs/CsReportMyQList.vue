<template>
  <el-col :span="12" class="cs-list-inner">
    <el-row>
      <el-col class="q-list-title title">
        면접 질문
      </el-col>
    </el-row>
    <el-row class="q-list-row">
      <el-scrollbar>
        <!-- <el-col>
          총 N문제 중 <i class="fas fa-check-circle best-fa"></i>가 n개,
          <i class="fas fa-exclamation-triangle not-bad-fa"></i>가 m개,
          <i class="fas fa-times-circle worst-fa"></i>가 l개입니다.
        </el-col> -->
        <!-- stat progress bar -->
        <el-col>
          <el-row class="progress-row">
            <el-col :span="11" v-for="(val, i) in type" class="progress-item">
              <span class="progress-category" v-if="average[i] != -1">{{ val }}</span>
              <span class="category-disabled" v-if="average[i] == -1">{{ val }}</span>
              <el-progress
                v-if="average[i] != -1"
                :stroke-width="18"
                :text-inside="true"
                :percentage="average[i]"
                :color="customColorMethod"
              />
              <div v-if="average[i] == -1" class="uddata"></div>
            </el-col>
            <!-- all questions -->
            <el-col :span="11" class="more-box">
              <el-button class="more-btn" @click="TotalQlistDialogVisible = true">
                채점 상세 보기
              </el-button>
            </el-col>
          </el-row>
        </el-col>
        <!-- report comment -->
        <el-row class="report-comment">
          <el-col :span="3" class="comment-title title">
            메모
          </el-col>
          <el-col class="comment-content">
            <div class="fixed">
              {{ props.checklistRes?.allCmt }}
            </div>
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-row>
  </el-col>

  <!-- My Question List Modal Dialog -->
  <el-dialog v-model="TotalQlistDialogVisible" width="40%">
    <el-scrollbar>
      <div class="cs-modal-container">
        <el-row class="modal-title">
          전체 기술 면접 질문
        </el-row>
        <el-row>
          <el-tabs v-model="activeName" class="cs-list-tab" @tab-click="handleClick">
            <el-tab-pane label="전체" name="total" class="dialog-inner">
              <el-scrollbar>
                <el-row v-for="(val, i) in type">
                  <div class="category">
                    {{ val }}
                  </div>
                  <el-col :span="23" class="cs-list-item" v-for="answer in checklistRes.answerRes" :key="answer.questNo">
                    <span v-if="answer.csScore == 3 && answer.type == i">
                      <i class="fas fa-check-circle best-fa"></i> {{ answer.quest }}
                    </span>
                    <span v-else-if="answer.csScore == 2 && answer.type == i">
                      <i class="fas fa-exclamation-triangle not-bad-fa"></i> {{ answer.quest }}
                    </span>
                    <span v-else-if="answer.csScore == 1 && answer.type == i">
                      <i class="fas fa-times-circle worst-fa"></i> {{ answer.quest }}
                    </span>
                  </el-col>
                </el-row>
              </el-scrollbar>
            </el-tab-pane>

            <!-- 정답 탭 -->
            <el-tab-pane class="dialog-inner">
              <template #label>
                <i class="fas fa-check-circle best-fa"></i>
              </template>
              <el-scrollbar>
                <el-row v-for="(val, i) in type">
                  <div class="category">
                    {{ val }}
                  </div>
                  <el-col :span="23" class="cs-list-item" v-for="answer in checklistRes.answerRes" :key="answer.questNo">
                    <span v-if="answer.csScore == 3 && answer.type == i">
                      <i class="fas fa-check-circle best-fa"></i> {{ answer.quest }}
                    </span>
                  </el-col>
                </el-row>
              </el-scrollbar>
            </el-tab-pane>
            
            <!-- 세모 탭 -->
            <el-tab-pane class="dialog-inner">
              <template #label>
                <i class="fas fa-exclamation-triangle not-bad-fa"></i>
              </template>
              <el-scrollbar>
                <el-row v-for="(val, i) in type">
                  <div class="category">
                    {{ val }}
                  </div>
                  <el-col :span="23" class="cs-list-item" v-for="answer in checklistRes.answerRes" :key="answer.questNo">
                    <span v-if="answer.csScore == 2 && answer.type == i">
                      <i class="fas fa-exclamation-triangle not-bad-fa"></i> {{ answer.quest }}
                    </span>
                  </el-col>
                </el-row>
              </el-scrollbar>
            </el-tab-pane>
            
            <!-- 오답 탭 -->
            <el-tab-pane class="dialog-inner">
              <template #label>
                <i class="fas fa-times-circle worst-fa"></i>
              </template>
              <el-scrollbar>
                <el-row v-for="(val, i) in type">
                  <div class="category">
                    {{ val }}
                  </div>
                  <el-col :span="23" class="cs-list-item" v-for="answer in checklistRes.answerRes" :key="answer.questNo">
                    <span v-if="answer.csScore == 1 && answer.type == i">
                      <i class="fas fa-times-circle worst-fa"></i> {{ answer.quest }}
                    </span>
                  </el-col>
                </el-row>
              </el-scrollbar>
            </el-tab-pane>
          </el-tabs>
        </el-row>
      </div>
    </el-scrollbar>
  </el-dialog>    

</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useStore } from 'vuex';
const store = useStore()

const props = defineProps({
  checklistRes: Object,
  average: Object,
})

// CS question list dialog
const TotalQlistDialogVisible = ref(false)
const activeName = ref('total')


// progress bar
const type = {
  21: "운영체제",
  22: "웹",
  23: "데이터베이스",
  24: "네트워크",
  25: "알고리즘",
  26: "언어",
  29: "기타",
}

const customColorMethod = (percentage) => {
  if (percentage < 25) {
    return '#f23030'
  }
  else if (percentage < 50) {
    return '#f5c127'
  }
  else if (percentage < 75) {
    return '#2abf54'
  }
  else {
    return '#1892de'
  }
}





</script>

<style>
</style>
