<template>
  <el-col :span="12" class="real-list-content right-container">
    <el-row class="real-rate">
      <el-col class="q-list-title title">
        총점
      </el-col>
      <el-col>
          <el-rate
            v-model="star"
            disabled
            :texts="['노력해야지 😭😭', '다음엔 더 😓😓', '괜찮았어 😉😉', '어쩌면 붙을지도 😁😁', '합격한다! 🥰🥰']"
            allow-half
            show-text
          />
      </el-col>
    </el-row>
    <el-row class="report-simple-comment">
      <el-col class="simple-title title">
        한줄평
      </el-col>
      <el-col>
        {{ oneCmt }}
      </el-col>
    </el-row>
    <el-row class="real-memo">
      <el-col class="title">
        메모
      </el-col>
      <el-col class="memo-textarea">
        {{ allCmt }}
      </el-col>
    </el-row>
    <el-row class="real-tags">
      <el-col class="title">
        나만의 태그
      </el-col>
      <el-col class="tag-content">
        <el-tag
          v-for="tag in tagList"
          :key="tag"
          class="mx-1"
        >
          {{ tag }}
        </el-tag>
      </el-col>
    </el-row>
  </el-col>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  star: Number,
  allCmt: String,
  oneCmt: String,
  tags: String,
})

const tagArray = ref([])
const rateValue = ref(3.5)
const realReportMemo = ref('')

// tag
const inputValue = ref('')
const tagList = ref([])
tagList.value = props.tags?.substring(1).split("#")

const inputVisible = ref(false)

const handleClose = (tag) => {
  props.tags.splice(dynamicTags.value.indexOf(tag), 1)
}

const showInput = () => {
  inputVisible.value = true
}

const handleInputConfirm = () => {
  if (inputValue.value) {
    props.tags.push(inputValue.value)
  }
  inputVisible.value = false
}



</script>
<style>
  
</style>