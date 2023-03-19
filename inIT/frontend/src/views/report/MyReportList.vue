<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img src="@/assets/images/inIT.png" alt="logo" class="header-logo">
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar />
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <div class="report-list">
              <el-row class="report-list-top">
                <el-col :span="17" class="report-list-title">
                  면접 일지 리스트
                </el-col>
                <el-col :span="3">
                  <el-button class="link-real" @click="linkToReal">실전면접 작성</el-button>
                </el-col>
                <el-col class="select-box" :span="4">
                  <el-select @change="changeType" v-model="typeValue" class="m-2" placeholder="분류를 선택하세요">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    />
                  </el-select>
                </el-col>
              </el-row>
              <!-- style 옮기기 -->
              <el-scrollbar style="height: 85%;">
                <el-timeline>
                  <el-timeline-item v-for="item in tableData" :key="item.id" center :timestamp="item.createdAt"
                    placement="top" size="large" :color="TypeColorList[item.type]">
                    <el-card shadow="never" @click="linkTo(item.type, item.id)">
                      <el-row>
                        <el-col class="report-title">
                          {{ item.title }}
                        </el-col>
                      </el-row>
                      <el-row>
                        <el-col :span="4">
                          <span v-for="tag in item.tags" :key="tag">
                            <span v-if="item.tags != ''">
                              #{{ tag }}
                            </span>
                          </span>
                        </el-col>
                        <el-col :span="4">
                          <el-rate v-model="item.star" size="small" disabled show-score text-color="#ff9900"
                            score-template="{value}" />
                        </el-col>
                        <el-col :offset="6" :span="10">
                          {{ item.oneCmt }}
                        </el-col>
                      </el-row>
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </el-scrollbar>
              <el-row class="below-filter">
                <el-col :offset="9" :span="6" class="filter">
                  <el-input
                    v-model="tagFilterInput"
                    placeholder="태그를 검색하세요"
                    class="input-with-select"
                    @change="tagFilter"
                  >
                    <template #append>
                      <el-button>검색</el-button>
                    </template>
                  </el-input>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-main>
        <!-- main end -->
      </el-container>
      <!-- container end -->
    </el-container>
  </div>
</template>

<script setup>
import SideBar from "@/views/main/SideBar.vue"
import { ref, reactive, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

const router = useRouter();
const store = useStore()
const userPk = JSON.parse(sessionStorage.User).id

const tableData = computed(() => store.getters.reports)

onMounted(() => {
  store.dispatch('fetchReports', userPk)
});

const linkToReal = () => router.push({ name: "MyReportRealCreate" });

const linkTo = (type, reportPk) => {
  if (type == 1) {
    router.push({ name: "MyReportGeneralDetail", params: { reportPk: reportPk } })
  } else if (type == 2) {
    router.push({ name: "MyReportCsDetail", params: { reportPk: reportPk } })
  } else if (type == 3) {
    router.push({ name: "MyReportRealDetail", params: { reportPk: reportPk } })
  }
};


// category
const typeValue = ref("")
const options = [
  {
    value: 1,
    label: '일반 면접',
  },
  {
    value: 2,
    label: '기술 면접',
  },
  {
    value: 3,
    label: '실전 면접',
  },
]
const changeType = () => {
  const typeData = ref({
    userPk: userPk,
    type: typeValue.value,
  })
  store.dispatch('fetchTypeReports', typeData)
};


// tag
// tableData.forEach(report => {
//   report.tags = report.tags.splice(report.tags.indexOf(tags), 1)
// });


// tag filter
const tagFilterInput = ref("")

const tagFilter = () => {
  const filterData = ref({
    userPk: userPk,
    searchTag : tagFilterInput.value
  })
  store.dispatch("fetchTagReports", filterData)
}

// 내용
const TypeColorList = {
  1: "#fccb00", 
  2: "#004dcf", 
  3: "#f44336",
}

</script>

<style>
</style>