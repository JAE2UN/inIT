<template>
  <el-col :span="12" class="cs-detail d-flex">
    <el-row class="more">
      <el-col class="title" :span="18">
        나의 체크리스트
      </el-col>
      <el-col :span="6" class="total-check">
        <TotalChecklist :checklist="checklist"/>
      </el-col>
      <el-col v-for="i in 3" class="average" :span="8">
        <span class="av-category">{{ Category[i] }}</span> 평균: <span class="av-num">{{ average[i] }}</span>
      </el-col>
    </el-row>
    <!-- star rate -->
    <el-row class="report-rate">
      <el-col class="rate-inner">
        <span class="rate-title">나의 별점</span>
        <span class="report-star">
          <el-rate
            v-model="reportData.star"
            :texts="['힘을 내요 😭😭', '조금만 더 😓😓', '괜찮았어요😉😉', '잘했어요 😁😁', '완전 최고! 🥰🥰']"
            allow-half
            show-text
            disabled
          />
        </span>
      </el-col>
    </el-row>
    <!-- report simple comment -->
    <el-row class="report-simple-comment">
      <el-col :span="5" class="simple-title title">
        한줄평
      </el-col>
      <!-- <el-col :span="1" class="simple-edit">
        <span class="edit-btn" @click="switchIsEditing2">
          <i class="fas fa-pen"></i>
        </span>
      </el-col> -->
      <el-col class="simple-content">
        <div class="fixed">
          {{ reportData.oneCmt }}
        </div>
      </el-col>
      <!-- <el-col v-if="isEditing">  
        <el-input
          placeholder="한줄평을 입력하세요."
          v-model="reportData.oneCmt"
          @change="switchIsEditing"
          maxlength="35"
          show-word-limit
        >
        </el-input>
        <el-divider class="divider-line"/>
      </el-col> -->
    </el-row>
    <!-- report tags -->
    <el-row class="report-tag">
      <el-col class="tag-title title">
        나만의 태그 {{ reportData }}
      </el-col>
      <el-col class="tag-content">
        <el-tag
          v-for="tag in reportData.tags?.substring(1).split('#')"
          :key="tag"
          class="mx-1"
          :disable-transitions="false"
          @close="handleClose(tag)"
        >
          {{ tag }}
        </el-tag>
        <!-- <el-input
          v-if="inputVisible"
          ref="InputRef"
          v-model="inputValue"
          class="ml-1 w-20"
          size="small"
          @keyup.enter="handleInputConfirm"
          @blur="handleInputConfirm"
        />
        <el-button v-if="isEditing && !inputVisible" class="button-new-tag ml-1" size="small" @click="showInput">
          + New Tag
        </el-button> -->
      </el-col>
    </el-row>
  </el-col>

</template>


<script setup>
import { ref, computed, onMounted } from 'vue'
import TotalChecklist from "@/views/report/components/general/TotalChecklist.vue"
import { useStore } from 'vuex';
import { useRouter, useRoute } from "vue-router";


// fetch
const store = useStore()
const route = useRoute()
const reportPk = route.params.reportPk
const reportData = computed(() => store.getters.report)
const checklist = computed(() => store.getters.report)
const average = computed(() => store.getters.average)

// checklist average
const Category = {
  1: '태도',
  2: '내용',
  3: '기타'
}

// tag
// const inputValue = ref('')
// const inputVisible = ref(false)

const tagList = ref([])
tagList.value = reportData.value.tags?.substring(1).split("#")

const handleClose = (tag) => {
  tagList.value.splice(tagList.value.indexOf(tag), 1)
}

// const showInput = () => {
//   inputVisible.value = true
// }

// const handleInputConfirm = () => {
//   if (inputValue.value) {
//     tagList.value.push(inputValue.value)
//   }
//   inputVisible.value = false
//   inputValue.value = ''
// }
</script>

<style>
  
</style>