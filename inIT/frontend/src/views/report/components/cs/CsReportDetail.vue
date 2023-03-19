<template>
    <el-col :span="12" class="cs-detail d-flex">

      <!-- star rate -->
      <el-row class="report-rate">
        <el-col class="rate-inner">
          <span class="rate-title">나의 별점</span>
          <span class="report-star">
            <el-rate
              v-model="props.star"
              :texts="['힘을 내요 😭😭', '조금만 더 😓😓', '괜찮았어요😉😉', '잘했어요 😁😁', '완전 최고! 🥰🥰']"
              allow-half
              show-text
              disabled
            />
          </span>
        </el-col>
      </el-row>
      <!-- report comment -->
      <!-- <el-row class="report-comment">
        <el-col :span="3" class="comment-title title">
          메모
        </el-col>
        <el-col :span="1" class="comment-edit">
          <span class="edit-btn" @click="switchIsEditing">
            <i class="fas fa-pen"></i>
          </span>
        </el-col>
        <el-col class="comment-content">
          <div v-if="isEditing" class="fixed">
            {{ interviewData.comment }}
          </div>
          <el-input
            v-if="!isEditing" 
            @change="switchIsEditing"
            v-model="interviewData.comment"
            placeholder="수정안됨"
            :rows="3"
          />
        </el-col>
      </el-row> -->
      <!-- report simple comment -->
      <el-row class="report-simple-comment">
        <el-col :span="3" class="simple-title title">
          한줄평
        </el-col>
        <el-col class="simple-content">
          <div class="fixed">
            {{ props.oneCmt }}
          </div>
        </el-col>
        <!-- <el-col v-if="isEditing">
          <el-input
            placeholder="한줄평을 입력하세요."
            v-model="interviewData.simpleComment"
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
          나만의 태그
        </el-col>
        <el-col class="tag-content">
          <el-tag
            v-for="tag in tagList"
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
          />
          <el-button v-if="!inputVisible" class="button-new-tag ml-1" size="small" @click="showInput">
            + New Tag
          </el-button> -->
        </el-col>
      </el-row>
    </el-col>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  star: Number,
  oneCmt: String,
  tags: String,
})

// tags
// const inputValue = ref('')
// const inputVisible = ref(false)

const tagList = ref([])
tagList.value = props.tags?.substring(1).split("#")

const handleClose = (tag) => {
  tagList.value.splice(tagList.value.indexOf(tag), 1)
}

// const showInput = () => {
//   inputVisible.value = true
// }

// const handleInputConfirm = () => {
//   if (inputValue.value) {
//     console.log("hey")
//     tagList.value.push(inputValue.value)
//   }
//   inputVisible.value = false
//   inputValue.value = ''
// }
</script>
<style>
  
</style>