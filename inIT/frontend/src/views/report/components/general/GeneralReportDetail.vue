<template>
  <el-col :span="12" class="review-video">
    <div class="left-section">
      <el-row class="expired-row">
        <el-col :span="20" class="expired-title">
          면접 문항
        </el-col>
        <el-col :span="4" class="expired-video">
          <el-button text @click="dialogVisible = true">
            <i class="fas fa-play-circle icon-m"></i> 다시 보기
          </el-button>
        </el-col>
        <el-col v-if="isReportLoad">
          <el-row v-for="check in props.checklistRes.answerRes">
            <el-col v-if="check.flag == 1" class="checkquest">
              <i class="fas fa-check"></i> {{ check.quest }}
            </el-col>
          </el-row>
        </el-col>
      </el-row>
      <el-divider/>
      <el-row class="allCmt">
        <el-col :span="3" class="comment-title">
          총평 {{ qCount }}
        </el-col>
        <el-col class="fixed-comment" v-if="isReportLoad">
          {{ checklistRes?.allCmt }}
        </el-col>          
      </el-row>
    </div>
  </el-col>

  <el-dialog
    v-model="dialogVisible"
    width="40%"
    v-if="isLoaded"
  >
    <el-col class="question-video">
      <div v-if="isLoaded">
        <video class="reviewVideo" :src="videoURL[currentQ]?.url" controls>
          <!-- <source :src="videoURL[currentQ]?.url" type="video/mp4"> -->
          해당 브라우저는 video 태그를 지원하지 않습니다.
        </video>
      </div>
    </el-col>
    <el-col class="pageQ" v-if="questionList[currentQ].flag == 1">
      <span class="q-num">Q{{ currentQ + 1 }}. </span>
      {{ questionList[currentQ].quest }}
    </el-col>
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
  </el-dialog>

</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";

const props = defineProps({
  checklistRes: Object,
})

const store = useStore()
const isReportLoad = computed(() => store.getters.isReportLoad)

// const generalQs = ref([])
// props.checklistRes?.answerRes?.forEach(answer => {
//   if ( answer.flag == 1) {
//     generalQs.value.push(answer.quest)
//   }
// })

const dialogVisible = ref(false)

const hey = ref(false)
const totalQ = props.checklistRes?.answerRes?.length * 10
// if ( totalQ)
const currentQ = ref(0)
const setQuestion = (page) => {
  currentQ.value = page - 1
}





const route = useRoute();
let keyIdx = route.params.reportPk;

const Category = {
  1: "태도",
  2: "내용",
  3: "기타",
};

const questionList = computed(() => store.getters.questionList);
const interviewData = computed(() => store.getters.interview);
const comment = computed(() => store.getters.allCmt);
const videoURL = computed(() => store.getters.videos)
const isLoaded = computed(() => store.getters.isLoaded)
const qCount = computed(() => store.getters.qCount)


const fetchInfo = {
  reportPk: keyIdx,
  userPk: JSON.parse(sessionStorage.User).id
}

onMounted(() => {
  // store.dispatch("API_Test", keyIdx);
  store.dispatch("webRTC", fetchInfo)
});

</script>
<style>
  
</style>