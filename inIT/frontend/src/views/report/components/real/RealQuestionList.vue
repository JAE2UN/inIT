<template>
  <el-col :span="12" class="real-list-q">
    <el-scrollbar>
      <div class="real-container">
        <el-row class="interview-info">
          <el-col>회사 정보: {{ reportData.company }}</el-col>
          <el-col>면접 종류: {{ reportData.info }}</el-col>
          <el-col>면접일자: {{ reportData.realDate }}</el-col>
        </el-row>
        <el-row class="q-list-title title">
          면접 질문
        </el-row>
        <el-row class="real-q" v-for="i in 3">
          <el-col v-if="!reportData.realQaRes" class="notice">
            '수정 / 추가' 버튼으로 면접 질문을 복기해보세요!
          </el-col>
          <!-- v-else -->
          <el-col class="category">{{ category[i] }}</el-col>
          <el-row class="real-q" v-for="q in reportData.realQaRes">
            <el-col :span="24" v-if="parseInt( q.type / 10 ) == i">
              <i class="fas fa-check"></i> {{ q.question }}
            </el-col>  
          </el-row>
        </el-row>
      </div>
    </el-scrollbar>
  </el-col>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router"

// fetch
const route = useRoute()
const reportPk = route.params.reportPk
const store = useStore()
const reportData = computed(() => store.getters.report)

onMounted(() => {
  store.dispatch('fetchMyReportReal', reportPk)
});


// interview questions
const category = {
  1: "인적성",
  2: "CS",
  3: "프로젝트",
}

const undef = () => {
  return reportData.realQaRes == "undefined"
}

// const findType = (arr, type) => {
//   // return arr.filter(data => parseInt(data.type / 10) == type)
// }

const value1 = ref('')

const removequestion = (item) => {
  const index = qList.questions.indexOf(item)
  if (index !== -1) {
    qList.questions.splice(index, 1)
  }
}

const addquestion = () => {
  qList.questions.push({
    key: Date.now(),
    value: '',
  })
}

// modal dialog
const TotalQlistDialogVisible = ref(false)

</script>
