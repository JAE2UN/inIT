import router from "../../router";
import http from "@/api/http.js";
import axios from 'axios';

const report = {
  state: {
    reports: [],
    report: {},
    checklist: {},
    average: {},
    qCount: 0,
    isReportLoad: false,
  },
  mutations: {
    SET_REPORTS: (state, reports) => (state.reports = reports),
    SET_REPORT: (state, report) => (state.report = report),
    SET_CHECKLIST: (state, checklist) => (state.checklist = checklist),
    SET_AVERAGE: (state, average) => (state.average = average),
    SET_Q_COUNT: (state, qCount) => (state.qCount = qCount),
    SET_IS_REPORT_LOAD: (state, isReportLoad) => (state.isReportLoad = isReportLoad),
  },
  getters: {
    reports: (state) => state.reports,
    report: (state) => state.report,
    checklist: (state) => state.checklist,
    average: (state) => state.average,
    qCount: (state) => state.qCount,
    isReportLoad: (state) => state.isReportLoad,
  },
  actions: {
    // report list
    fetchReports({ commit }, userPk) {
      http.get(`/report/All/${userPk}`)
        .then(({ data }) => {
          data.forEach(data => {
            // data.createdAt = data.createdAt.substring(2, 10)
            data.tags = data.tags?.substring(1).split("#")
          })
          commit("SET_REPORTS", data);
        })
        .catch((err) => console.log(err));
    },
    fetchTypeReports({ commit }, fetchInfo) {
      http.get(`/report/All/${fetchInfo.value.userPk}/${fetchInfo.value.type}`)
        .then(({ data }) => {
          data.forEach(data => {
            data.tags = data.tags?.substring(1).split("#")
          });
          commit("SET_REPORTS", data);
        })
        .catch((err) => console.log(err));
    },
    fetchTagReports({ commit }, fetchInfo) {
      http.get(`/report/search/${fetchInfo.value.userPk}/${fetchInfo.value.searchTag}`)
        .then(({ data }) => {
          data.forEach(data => {
            data.tags = data.tags?.substring(1).split("#")
          });
          commit("SET_REPORTS", data);
        })
        .catch((err) => console.log(err));
    },

    // GENERAL Myreport RUD
    fetchMyReport({ commit }, reportPk) {
      commit('SET_IS_REPORT_LOAD', false)
      // http.get(`/report/detail/${reportPk}`)
      //   .then(({ data }) => {
      //     commit("SET_REPORT", data);
      //     commit('SET_IS_REPORT_LOAD', true);
      //   })
      // http.get(`/dashboard/${reportPk}`)
      //   .then(({ data }) => {
      //     commit("SET_AVERAGE", data)
      //   })
      axios.all([
        http.get(`/report/detail/${reportPk}`), 
        http.get(`/dashboard/${reportPk}`),
      ])
        .then(
          axios.spread((res1, res2) => {
            commit("SET_REPORT", res1.data);
            commit("SET_AVERAGE", res2.data)
            // let qCount = 0;
            // for (let i = 0; i < res1.checklistRes.answerRes.length; i++) {
            //   if (res1.type == 1 && res1.checklistRes.answerRes.flag == 1) {
            //     qCount++;
            //   }              
            // }
            // commit("SET_Q_COUNT", qCount)
            commit('SET_IS_REPORT_LOAD', true);
          }))
        .catch((err) => console.log(err))
    },
    editMyReport({ }, data) {
      http.post("/report/update", data)
        .then((res) => {
          router.push({ name: "MyReportGeneralDetail", params: { reportPk: res.data.id } });
        })
        .catch((err) => console.log(err));
    },

    // CS MyRepoort CUD
    fetchMyReportCs({ commit }, reportPk) {
      commit('SET_IS_REPORT_LOAD', false)
      axios.all([
        http.get(`/report/detail/${reportPk}`), 
        http.get(`/dashboard/${reportPk}`),
      ])
        .then(
          axios.spread((res1, res2) => {
            // res1.data.createdAt = res1.data.createdAt?.substring(2, 10)
            commit("SET_REPORT", res1.data);
            commit("SET_AVERAGE", res2.data)
            commit('SET_IS_REPORT_LOAD', true);
          }))
        .catch((err) => console.log(err))
    },
    editMyReportCs({ }, data) {
      http.post("/report/update", data)
        .then((res) => {
          router.push({ name: "MyReportCsDetail", params: { reportPk: res.data.id } });
        })
        .catch((err) => console.log(err));
    },

    // REAL MyReport CRUD
    createMyReportReal({ }, data) {
      http.post("/realreport/insert", data)
        .then((res) => {
          router.push({ name: "MyReportRealDetail", params: { reportPk: res.data.id } });
        })
        .catch((err) => console.log(err));
    },
    fetchMyReportReal({ commit }, reportPk) {
      commit('SET_IS_REPORT_LOAD', false)
      http.get(`/realreport/${reportPk}`)
        .then(({ data }) => {
          commit("SET_REPORT", data);
          commit('SET_IS_REPORT_LOAD', true)
        })
        .catch((err) => console.log(err))
    },
    editMyReportReal({ }, data) {
      http.post("/realreport/update", data)
        .then((res) => {
          router.push({ name: "MyReportRealDetail", params: { reportPk: res.data.id } });
        })
        .catch((err) => console.log(err));
    },
    deleteMyReportReal({ }, data) {
      http.delete(`/realreport/delete/${data.reportPk}/${data.userPk}`)
        .then((res) => {
          router.push({name: 'MyReportList'})
        })
        .catch((err) => console.log(err));
    },


  },
}

export default report;
