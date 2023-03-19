<template>
  <div class="common-layout">
    <el-container>
      <el-header class="d-flex" height="100px">
        <img
          src="@/assets/images/inIT.png"
          alt="logo"
          class="header-logo"
          @click="movePageTo('Home')"
        />
      </el-header>
      <!-- header end -->
      <el-container class="entire-container">
        <el-aside width="220px">
          <side-bar />
        </el-aside>
        <!-- side-bar end -->
        <el-main>
          <div class="main-container container-box">
            <!-- inner: 하나의 큰 박스 -> 바꾸고 싶다면 .inner 해제 -->
            <div class="inner">
              <el-scrollbar class="inner-scroll">
                <!-- <el-form :model="form"> -->
                <el-collapse class="myqlist-main" v-model="active">
                  <el-collapse-item
                    class="myqlist-main-cate"
                    title="일반"
                    name="1"
                  >
                    <el-row
                      class="myqlist-main-item d-flex"
                      v-for="q in QList"
                      :key="q.index"
                    >
                      <el-form
                        v-model="q.quest"
                        v-if="q.type == 10"
                        class="myqlist-main-item-form"
                        style="width: 100%"
                      >
                        <el-form-item>
                          <el-input type="hidden" :value="q.id"></el-input>
                        </el-form-item>
                        <el-form-item>
                          <el-input
                            v-model="q.quest"
                            class="myqlist-main-item-input"
                          >
                            <template #prepend>
                              <el-select
                                v-model="q.priority"
                                placeholder="중요도"
                                style="width: 60px"
                              >
                                <el-option
                                  v-for="item in optionsP"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                            </template>
                            <template #append>
                              <el-button
                                style="margin-right: 20px"
                                @click="updateQItem(q)"
                                >수정</el-button
                              >
                              <el-button @click="deleteQItem(q)"
                                >삭제</el-button
                              >
                            </template>
                          </el-input>
                        </el-form-item>
                      </el-form>
                    </el-row>
                    <el-row v-if="inputVisible1">
                      <el-input
                        type="hidden"
                        :value="(newType = 10)"
                      ></el-input>
                      <el-input
                        v-model="newQuest"
                        class="myqlist-main-item-input"
                      >
                        <template #prepend>
                          <el-select
                            v-model="newPriority"
                            placeholder="중요도"
                            style="width: 60px"
                          >
                            <el-option
                              v-for="item in optionsP"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </template>
                        <template #append>
                          <el-button
                            @click="insertQItem"
                            style="margin-right: 20px"
                            >등록</el-button
                          >
                          <el-button @click="discard1">취소</el-button>
                        </template>
                      </el-input>
                    </el-row>
                    <el-row
                      v-else
                      class="question-addBtn"
                      size="small"
                      @click="showInput1"
                    >
                      <el-button> 추가 </el-button>
                    </el-row>
                  </el-collapse-item>
                  <el-collapse-item
                    class="myqlist-main-cate"
                    title="CS"
                    name="2"
                  >
                    <el-row
                      class="myqlist-main-item"
                      v-for="q in QList"
                      :key="q.index"
                    >
                      <el-form
                        v-model="q.quest"
                        v-if="parseInt(q.type / 10) == 2"
                        class="myqlist-main-item-form"
                        style="width: 100%"
                      >
                        <el-form-item>
                          <el-input type="hidden">{{ q.id }}</el-input>
                        </el-form-item>
                        <el-form-item>
                          <el-input
                            v-model="q.quest"
                            class="myqlist-main-item-input"
                          >
                            <template #prepend>
                              <el-select
                                v-model="q.priority"
                                placeholder="중요도"
                                style="width: 60px; margin-right: 20px"
                              >
                                <el-option
                                  v-for="item in optionsP"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                              <el-select
                                v-model="q.type"
                                placeholder="카테고리"
                                style="width: 120px"
                              >
                                <el-option
                                  v-for="item in optionsC"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                            </template>
                            <template #append>
                              <el-button
                                style="margin-right: 20px"
                                @click="updateQItem(q)"
                                >수정</el-button
                              >
                              <el-button @click="deleteQItem(q)"
                                >삭제</el-button
                              >
                            </template>
                          </el-input>
                        </el-form-item>
                      </el-form>
                    </el-row>
                    <el-row v-if="inputVisible2">
                      <el-input
                        v-model="newQuest"
                        class="myqlist-main-item-input"
                      >
                        <template #prepend>
                          <el-select
                            v-model="newPriority"
                            placeholder="중요도"
                            style="width: 60px; margin-right: 20px"
                          >
                            <el-option
                              v-for="item in optionsP"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                          <el-select
                            v-model="newType"
                            placeholder="카테고리"
                            style="width: 120px"
                          >
                            <el-option
                              v-for="item in optionsC"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </template>
                        <template #append>
                          <el-button
                            @click="insertQItem"
                            style="margin-right: 20px"
                            >등록</el-button
                          >
                          <el-button @click="discard2">취소</el-button>
                        </template>
                      </el-input>
                    </el-row>
                    <el-row
                      v-else
                      class="question-addBtn"
                      size="small"
                      @click="showInput2"
                    >
                      <el-button> 추가 </el-button>
                    </el-row>
                  </el-collapse-item>
                  <el-collapse-item
                    class="myqlist-main-cate"
                    title="프로젝트"
                    name="3"
                  >
                    <el-row
                      class="myqlist-main-item"
                      v-for="q in QList"
                      :key="q.index"
                    >
                      <el-form
                        v-model="q.quest"
                        v-if="q.type == 30"
                        class="myqlist-main-item-form"
                        style="width: 100%"
                      >
                        <el-form-item>
                          <el-input type="hidden">{{ q.id }}</el-input>
                        </el-form-item>
                        <el-form-item>
                          <el-input
                            v-model="q.quest"
                            class="myqlist-main-item-input"
                          >
                            <template #prepend>
                              <el-select
                                v-model="q.priority"
                                placeholder="중요도"
                                style="width: 60px"
                              >
                                <el-option
                                  v-for="item in optionsP"
                                  :key="item.value"
                                  :label="item.label"
                                  :value="item.value"
                                />
                              </el-select>
                            </template>
                            <template #append>
                              <el-button
                                style="margin-right: 20px"
                                @click="updateQItem(q)"
                                >수정</el-button
                              >
                              <el-button @click="deleteQItem(q)"
                                >삭제</el-button
                              >
                            </template>
                          </el-input>
                        </el-form-item>
                      </el-form>
                    </el-row>
                    <el-row v-if="inputVisible3">
                      <el-input
                        type="hidden"
                        :value="(newType = 30)"
                      ></el-input>
                      <el-input
                        v-model="newQuest"
                        class="myqlist-main-item-input"
                      >
                        <template #prepend>
                          <el-select
                            v-model="newPriority"
                            placeholder="중요도"
                            style="width: 60px"
                          >
                            <el-option
                              v-for="item in optionsP"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                            />
                          </el-select>
                        </template>
                        <template #append>
                          <el-button
                            @click="insertQItem"
                            style="margin-right: 20px"
                            >등록</el-button
                          >
                          <el-button @click="discard3">취소</el-button>
                        </template>
                      </el-input>
                    </el-row>
                    <el-row
                      v-else
                      class="question-addBtn"
                      size="small"
                      @click="showInput3"
                    >
                      <el-button> 추가 </el-button>
                    </el-row>
                  </el-collapse-item>
                </el-collapse>
                <el-button @click="backToProfile">뒤로</el-button>
              </el-scrollbar>
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
import SideBar from "@/views/main/SideBar.vue";
import { useStore } from "vuex";
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import http from "../../api/http";

const store = useStore();
const router = useRouter();

const active = ["1", "2", "3"];
// const active = ["2"];

const optionsP = [
  {
    label: "상",
    value: 3,
  },
  {
    label: "중",
    value: 2,
  },
  {
    label: "하",
    value: 1,
  },
];

const optionsC = [
  {
    label: "운영체제",
    value: 21,
  },
  {
    label: "웹",
    value: 22,
  },
  {
    label: "데이터베이스",
    value: 23,
  },
  {
    label: "네트워크",
    value: 24,
  },
  {
    label: "알고리즘",
    value: 25,
  },
  {
    label: "언어",
    value: 26,
  },
  {
    label: "기타",
    value: 29,
  },
];

const userId = JSON.parse(sessionStorage.getItem("User")).id;

const newType = ref("");
const newQuest = ref("");
const newPriority = ref("");

const inputVisible1 = ref(false);
const inputVisible2 = ref(false);
const inputVisible3 = ref(false);

const showInput1 = () => {
  inputVisible1.value = true;
  inputVisible2.value = false;
  inputVisible3.value = false;
};
const showInput2 = () => {
  inputVisible1.value = false;
  inputVisible2.value = true;
  inputVisible3.value = false;
};
const showInput3 = () => {
  inputVisible1.value = false;
  inputVisible2.value = false;
  inputVisible3.value = true;
};
const discard1 = () => {
  inputVisible1.value = false;
};
const discard2 = () => {
  inputVisible2.value = false;
};
const discard3 = () => {
  inputVisible3.value = false;
};

const QList = computed(() => store.getters.qList);

const backToProfile = () => {
  router.go(-1);
};

const userQURL = "/userQuestion/";

const insertQItem = () => {
  // id 제외, priority, quest, type, userId
  if (newPriority.value && newQuest.value) {
    const insertQInfo = JSON.stringify({
      priority: newPriority.value,
      quest: newQuest.value,
      type: newType.value,
      userId: userId,
    });

    http
      .post(userQURL + "insert", insertQInfo)
      .then(() => {
        // alert("insertQ success");
        router.go();
      })
      .catch((err) => {
        alert("insertQ error");
        console.log(err);
      });
  }
};
const updateQItem = (item) => {
  // id, priority, quest, type, userId
  if (item.priority && item.quest) {
    const updateQInfo = JSON.stringify({
      id: item.id,
      priority: item.priority,
      quest: item.quest,
      type: item.type,
      userId: userId,
    });
    http
      .post(userQURL + "update", updateQInfo)
      .then(() => {
        // alert("updateQ success");
        router.go();
      })
      .catch((err) => {
        alert("updateQ error");
        console.log(err);
      });
  }
};
const deleteQItem = (item) => {
  // id, userId
  const deleteQInfo = JSON.stringify({
    id: item.id,
    userId: userId,
  });

  http
    .post(userQURL + "delete", deleteQInfo)
    .then(() => {
      // alert("deleteQ success");
      router.go();
    })
    .catch((err) => {
      alert("deleteQ error");
      console.log(err);
    });
};

onMounted(() => {
  const requestURL = userQURL + userId;
  http
    .get(requestURL)
    .then(({ data }) => {
      console.log("질문리스트 불러오기 성공");
      console.log(data);
      store.dispatch("readQList", data);
    })
    .catch((err) => {
      alert("질문리스트를 불러오는데 오류가 발생했습니다");
      console.log(err);
    });
});

// 페이지 이동
const movePageTo = (pageName) => {
  router.push({ name: pageName });
};
</script>

<style></style>
