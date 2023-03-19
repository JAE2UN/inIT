import router from "../../router";

import http from "@/api/http.js";
import axios from "axios";

const interview = {
  state: {
    interviews: [],
    videos: [],
    interview: {},
    isFinished: false,
    question: "",
    questionList: [
      {
        flag: 0,
        questNo: 0,
        quest: "",
        type: 0,
        evalScore: 0,
        csScore: 0,
      },
    ],
    questionCount: 0,
    allCmt: "",
    isLoaded: false,
  },
  mutations: {
    SET_INTERVIEWS: (state, interviews) => (state.interviews = interviews),
    SET_VIDEOS: (state, videos) => (state.videos = videos),
    SET_INTERVIEW: (state, interview) => (state.interview = interview),
    SET_QUESTION: (state, question) => (state.question = question),
    SET_IS_FINISHED: (state, isFinished) => (state.isFinished = isFinished),
    SET_QUESTION_LIST: (state, questionList) =>
      (state.questionList = questionList),
    SET_QUESTION_COUNT: (state, questionCount) =>
      (state.questionCount = questionCount),
    SET_ALL_CMT: (state, allCmt) => (state.allCmt = allCmt),
    SET_CHECK_LIST: (state, checkList) => (state.checkList = checkList),
    SET_IS_LOADED: (state, isLoaded) => (state.isLoaded = isLoaded),
  },
  getters: {
    interviews: (state) => state.interviews,
    videos: (state) => state.videos,
    interview: (state) => state.interview,
    question: (state) => state.question,
    isFinished: (state) => state.isFinished,
    questionList: (state) => state.questionList,
    questionCount: (state) => state.questionCount,
    allCmt: (state) => state.allCmt,
    checkList: (state) => state.checkList,
    isLoaded: (state) => state.isLoaded,
  },
  actions: {
    // fetchInterviewQs({ commit }, fetchInfo) {
    //   http.get("interview/ready").then();
    // },

    // general inteview
    InterviewSettingsGeneral({ }, fetchInfo) {
      http.post("/interview/ready", fetchInfo)
        .then(({ data }) => {
          router.push({ name: "Interview", params: { type: "general", reportPk: data } });
        })
        .catch((err) => console.log(err));
    },
    InterviewSettingsCs({ }, fetchInfo) {
      http.post("/interview/ready", fetchInfo)
        .then(({ data }) => {
          router.push({ name: "Interview", params: { type: "cs", reportPk: data } });
        })
        .catch((err) => console.log(err));
    },
    fetchInterviewQuestion({ commit }, questionPk) {
      http.post("interview/par", questionPk).then((res) => {
        commit("SET_QUESTION", res);
      });
    },

    // interview
    InterviewGeneral({ commit }, fetchInfo) {
      commit("SET_IS_FINISHED", false);
      http.post("/interview/quest", fetchInfo)
        .then(({ data }) => {
          console.log("now qNo.: ", data.nowNo)
          if (data.nowNo > -1) {
            console.log("question response")
            commit("SET_QUESTION", data);
          } else {
            console.log("isFinished")
            commit("SET_IS_FINISHED", true);
          }
        })
        .catch((err) => console.log(err));
    },

    InterviewCs({ commit }, fetchInfo) {
      commit("SET_IS_FINISHED", false);
      http.post("/interview/quest", fetchInfo)
        .then(({ data }) => {
          console.log("now CS qNo.: ", data.nowNo)
          if (data.nowNo > -1) {
            console.log("CS question response")
            commit("SET_QUESTION", data);
          } else {
            console.log("CS isFinished")
            commit("SET_IS_FINISHED", true);
          }
        })
        .catch((err) => console.log(err));
    },

    // interview review
    webRTC({ commit }, fetchInfo) {
      commit("SET_IS_LOADED", false)
      http.get(`/webrtc/${fetchInfo.reportPk}/${fetchInfo.userPk}`)
        .then(({ data }) => {
            commit("SET_VIDEOS", data)
            commit("SET_IS_LOADED", true)
        })
        .catch((err) => console.log(err))
      // http.get(`/interview/evaluate/${reportPk}`)
      //   .then(({ data }) => {
      //     commit("SET_INTERVIEW", data);
      //   })
      //   .catch((err) => console.log(err))
    },

    // cs review

    API_Test({ commit }, keyIdx) {
      // commit("asdf")
      const requestURL = "/interview/evaluate/" + keyIdx;
      http
        .get(requestURL)
        .then(({ data }) => {
          console.log(data);
          const allCmt = data.allCmt;
          commit("SET_ALL_CMT", allCmt);
          if (data.type == 1) {
            const questionList = data.answerRes;
            commit("SET_QUESTION_LIST", questionList);

            let questionCount = 0;
            for (let i = 0; i < questionList.length; i++) {
              if (questionList[i].flag == 1) {
                questionCount++;
              }
            }
            console.log("qc : " + questionCount);
            commit("SET_QUESTION_COUNT", questionCount * 10);
          }
        })
        .catch((err) => console.log(err));
    },


    submitGeneralEval({ }, fetchInfo) {
      console.log(fetchInfo)
      http.post("/check/write", fetchInfo)
        .then(({ data }) => {
          console.log(data)
          router.push({
            name: "MyReportGeneralEdit",
            params: { reportPk: fetchInfo.reportId },
          })
        })
        .catch((err) => console.log(err))
    }
  },
};

export default interview;
