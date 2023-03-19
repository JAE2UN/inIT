import http from "@/api/http.js";

const dashboard = {
  state: {
    // 최근 5회 일반 면접 기록 리스트 개수
    CMT5: null,

    // 마지막 날짜 카테고리별 점수
    CMLast: [],

    // 차트 데이터 (날짜, 카테고리별 평균)
    CMChart: {
      labels: [], // date
      datasets: [
        {
          label: "태도",
          backgroundColor: "#fccb00",
          data: [],
        },
        {
          label: "내용",
          backgroundColor: "#004dcf",
          data: [],
        },
        {
          label: "기타",
          backgroundColor: "#f44336",
          data: [],
        },
      ],
    },
    maxScore: 0,
    minScore: 5,
    CMIsLoaded: false,

    // 최근 5회 CS 기록 리스트 개수
    CST5: null,

    // 카테고리별 맞춘문제, 틀린문제
    CSBSG: {
      bad: [0, 0, 0, 0, 0, 0, 0],
      soso: [0, 0, 0, 0, 0, 0, 0],
      good: [0, 0, 0, 0, 0, 0, 0],
    },

    // 차트 데이터 (카테고리, 문제 개수)
    CSChart: {
      labels: [
        "기타",
        "운영체제",
        "웹",
        "데이터베이스",
        "네트워크",
        "알고리즘",
        "언어",
      ],
      datasets: [
        {
          backgroundColor: [
            "#909399",
            "#F56C6C",
            "#E6A23C",
            "#67C23A",
            "#409EFF",
            "#FF40F5",
            "#DD1B16",
          ],
          data: [0, 0, 0, 0, 0, 0, 0], // 카테고리별 누적 문제 갯수
        },
      ],
    },
  },
  getters: {
    CMT5: (state) => state.CMT5,
    CMLast: (state) => state.CMLast,
    CMChart: (state) => state.CMChart,
    CMMinScore: (state) => state.minScore,
    CMMaxScore: (state) => state.maxScore,
    CMIsLoad: (state) => state.CMIsLoaded,

    CST5: (state) => state.CST5,
    CSBSG: (state) => state.CSBSG,
    CSChart: (state) => state.CSChart,
  },
  mutations: {
    SET_CMT5: (state, length) => {
      state.CMT5 = length;
    },
    ADD_CMCHART_LABELS: (state, date) => {
      state.CMChart.labels.push(date);
    },
    ADD_CMLAST: (state, avgScore) => {
      state.CMLast.push(avgScore);
    },
    ADD_CMCHART_DATASETS: (state, { catIdx, avgScore }) => {
      state.CMChart.datasets[catIdx].data.push(avgScore);
    },
    SET_MINSCORE: (state, score) => {
      state.minScore = score;
    },
    SET_MAXSCORE: (state, score) => {
      state.maxScore = score;
    },
    SET_CMIsLoaded: (state) => {
      state.CMIsLoaded = true;
    },

    RESET_CMT5: (state) => {
      state.CMT5 = null;
    },
    RESET_CMLAST: (state) => {
      state.CMLast = [];
    },
    RESET_CMCHART: (state) => {
      state.CMChart = {
        labels: [], // date
        datasets: [
          {
            label: "태도",
            backgroundColor: "#fccb00",
            data: [],
          },
          {
            label: "내용",
            backgroundColor: "#004dcf",
            data: [],
          },
          {
            label: "기타",
            backgroundColor: "#f44336",
            data: [],
          },
        ],
      };
    },
    RESET_CM: (state) => {
      state.minScore = 5;
      state.maxScore = 0;
      state.CMIsLoaded = false;
    },

    SET_CST5: (state, length) => {
      state.CST5 = length;
    },
    ADD_CSBSG: (state, { catIdx, score }) => {
      if (score == 1) {
        state.CSBSG.bad[catIdx] += 1;
      } else if (score == 2) {
        state.CSBSG.soso[catIdx] += 1;
      } else if (score == 3) {
        state.CSBSG.good[catIdx] += 1;
      }
    },
    ADD_CSCHART: (state, catIdx) => {
      state.CSChart.datasets[0].data[catIdx]++;
    },
    RESET_CST5: (state) => {
      state.CST5 = null;
    },
    RESET_CSBSG: (state) => {
      state.CSBSG.bad = [0, 0, 0, 0, 0, 0, 0];
      state.CSBSG.soso = [0, 0, 0, 0, 0, 0, 0];
      state.CSBSG.good = [0, 0, 0, 0, 0, 0, 0];
    },
    RESET_CSCHART: (state) => {
      state.CSChart.datasets[0].data = [0, 0, 0, 0, 0, 0, 0];
    },
  },
  actions: {
    async readCMT5({ commit, state }) {
      commit("RESET_CMT5");
      commit("RESET_CMCHART");
      commit("RESET_CMLAST");
      commit("RESET_CM");
      const userId = JSON.parse(sessionStorage.getItem("User")).id;
      await http
        .get("/dashboard/commonTop5/" + userId)
        .then(({ data }) => {
          const idxList = Object.keys(data);
          const dataLength = idxList.length;
          commit("SET_CMT5", dataLength);
          for (let i = 0; i < dataLength; i++) {
            const date = Object.keys(data[idxList[i]])[0];
            const dateText = date.substring(5, 10); //
            commit("ADD_CMCHART_LABELS", dateText);
            let score = 0;
            for (let j = 0; j < 12; j++) {
              score += data[idxList[i]][date][j].evalScore;

              if (j % 4 == 3) {
                const catIdx = parseInt(j / 4);
                const avgScore = parseFloat(score / 4);
                // console.log(catIdx, avgScore);
                commit("ADD_CMCHART_DATASETS", { catIdx, avgScore });

                if (i == dataLength - 1) {
                  commit("ADD_CMLAST", avgScore);
                }
                score = 0;
              }
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });

      for (let i = 0; i < 3; i++) {
        const score = state.CMLast[i];
        if (state.maxScore < score) {
          commit("SET_MAXSCORE", score);
        }
        if (state.minScore > score) {
          commit("SET_MINSCORE", score);
        }
      }

      commit("SET_CMIsLoaded");

      // 최근 5회
    },

    async readCST5({ commit }) {
      commit("RESET_CST5");
      commit("RESET_CSBSG");
      commit("RESET_CSCHART");
      const userId = JSON.parse(sessionStorage.getItem("User")).id;
      await http
        .get("/dashboard/csTop5/" + userId)
        .then(({ data }) => {
          const dataLength = data.length;
          commit("SET_CST5", dataLength);
          for (let i = 0; i < dataLength; i++) {
            const catIdx = parseInt(data[i].type) - 20;
            const score = data[i].csScore;
            commit("ADD_CSBSG", { catIdx, score });
            commit("ADD_CSCHART", catIdx);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};

export default dashboard;
