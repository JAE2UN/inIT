import router from "../../router";
import http from "@/api/http.js";

const board = {
  state: {
    articles: [],
    article: {},
    comments: [],
    isLoad: false,
    totalElements: 0,
  },
  mutations: {
    SET_ARTICLES: (state, articles) => (state.articles = articles),
    SET_ARTICLE: (state, article) => (state.article = article),
    SET_COMMENTS: (state, comments) => (state.comments = comments),
    SET_IS_LOAD: (state, isLoad) => (state.isLoad = isLoad),
    SET_TOTAL_ELEMENTS: (state, totalElements) =>
      (state.totalElements = totalElements),
  },
  getters: {
    articles: (state) => state.articles,
    article: (state) => state.article,
    comments: (state) => state.comments,
    isArticle: (state) => !!state.article,
    isLoad: (state) => state.isLoad,
    totalElements: (state) => state.totalElements,
  },
  actions: {
    // article list
    fetchArticles({ commit }, page) {
      // console.log("input page : " + page);
      http
        .get("/board/paging/free?page=" + page)
        .then(({ data }) => {
          commit("SET_ARTICLES", data.content);
          commit("SET_TOTAL_ELEMENTS", data.totalElements);
        })
        .catch((err) => console.log(err));
    },
    fetchTypeArticles({ commit }, params) {
      const type = JSON.parse(params).type;
      const page = JSON.parse(params).page;
      http
        .get("/board/paging/type/" + type + "?page=" + page)
        .then(({ data }) => {
          commit("SET_ARTICLES", data.content);
          commit("SET_TOTAL_ELEMENTS", data.totalElements);
        })
        .catch((err) => console.log(err));
    },

    // article CRUD
    createArticle({}, data) {
      http
        .post("/board/write", data)
        .then((res) => {
          router.push({
            name: "GeneralArticleDetail",
            params: { articlePk: res.data },
          });
        })
        .catch((err) => console.log(err));
    },
    fetchArticle({ commit, dispatch }, fetchInfo) {
      commit("SET_IS_LOAD", false);
      http
        .get(`/board/find/${fetchInfo.articlePk}/${fetchInfo.userPk}`)
        .then(({ data }) => {
          commit("SET_ARTICLE", data);
          dispatch("fetchComments", data.id);
          commit("SET_IS_LOAD", true);
        })
        .catch((err) => console.log(err));
    },
    editArticle({}, data) {
      http
        .put("/board/modify", data)
        .then((res) => {
          router.push({
            name: "GeneralArticleDetail",
            params: { articlePk: data.id },
          });
        })
        .catch((err) => console.log(err));
    },
    deleteArticle({}, data) {
      http
        .delete(`/board/remove/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          router.push({ name: "GeneralArticleList" });
        })
        .catch((err) => console.log(err));
    },

    // comments
    fetchComments({ commit }, articlePk) {
      commit("SET_IS_LOAD", false);
      http
        .get("/boardcomment/filterInfo/" + articlePk)
        .then(({ data }) => {
          commit("SET_COMMENTS", data);
          commit("SET_IS_LOAD", true);
        })
        .catch((err) => console.log(err));
    },
    createComment({ dispatch }, data) {
      http
        .post("/boardcomment/write", data)
        .then((res) => {
          dispatch("fetchComments", data.boardId);
        })
        .catch((err) => console.log(err));
    },
    deleteComment({ dispatch }, data) {
      http
        .delete(`/boardcomment/remove/${data.commentPk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchComments", data.articlePk);
        })
        .catch((err) => console.log(err));
    },

    // article like & unlike
    articleLike({ dispatch }, data) {
      http
        .get(`/boardlikes/recommend/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchArticle", data);
        })
        .catch((err) => console.log(err));
    },
    articleUnlike({ dispatch }, data) {
      http
        .delete(`/boardlikes/remove/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchArticle", data);
        })
        .catch((err) => console.log(err));
    },

    // feedback article list
    fetchFeedbackArticles({ commit }, page) {
      http
        .get("/feedback/paging?page=" + page)
        .then(({ data }) => {
          commit("SET_ARTICLES", data.content);
          commit("SET_TOTAL_ELEMENTS", data.totalElements);
        })
        .catch((err) => console.log(err));
    },
    // fetchFeedbackArticles({ commit }) {
    //   http
    //     .get("/feedback/findAll")
    //     .then(({ data }) => {
    //       commit("SET_ARTICLES", data);
    //     })
    //     .catch((err) => console.log(err));
    // },

    // feedback article CFUD
    createFeedbackArticle({}, data) {
      console.log(data);
      http
        .post("/feedback/write", data)
        .then((res) => {
          console.log(res);
          router.push({
            name: "FeedbackArticleDetail",
            params: { articlePk: res.data },
          });
        })
        .catch((err) => console.log(err));
    },
    fetchFeedbackArticle({ commit, dispatch }, articlePk) {
      commit("SET_IS_LOAD", false);
      http.get("/feedback/find/" + articlePk).then(({ data }) => {
        commit("SET_ARTICLE", data);
        dispatch("fetchFeedbackComments", data.id);
        commit("SET_IS_LOAD", true);
      });
    },
    editFeedbackArticle({}, data) {
      console.log("editAxios", data);
      http
        .put("/feedback/modify", data)
        .then((res) => {
          router.push({
            name: "FeedbackArticleDetail",
            params: { articlePk: data.id },
          });
        })
        .catch((err) => console.log(err));
    },
    deleteFeedbackArticle({}, data) {
      console.log(data);
      http
        .delete(`/feedback/remove/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          router.push({ name: "FeedbackArticleList" });
        })
        .catch((err) => console.log(err));
    },

    // feedback comments
    fetchFeedbackComments({ commit }, articlePk) {
      commit("SET_IS_LOAD", false);
      http
        .get("/feedbackcomment/filterInfo/" + articlePk)
        .then(({ data }) => {
          commit("SET_COMMENTS", data);
          commit("SET_IS_LOAD", true);
        })
        .catch((err) => console.log(err));
    },
    createFeedbackComment({ dispatch }, data) {
      http
        .post("/feedbackcomment/write", data)
        .then((res) => {
          dispatch("fetchFeedbackComments", data.feedbackId);
        })
        .catch((err) => console.log(err));
    },
    deleteFeedbackComment({ dispatch }, data) {
      http
        .delete(`/feedbackcomment/remove/${data.commentPk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchFeedbackComments", data.articlePk);
        })
        .catch((err) => console.log(err));
    },

    //feedback comment pick & unpick
    commentPick({ dispatch }, data) {
      http
        .get(`/feedbackcomment/pick/${data.commentPk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchFeedbackComments", data.articlePk);
        })
        .catch((err) => console.log(err));
    },
    commentUnpick({ dispatch }, data) {
      http
        .get(`/feedbackcomment/unpick/${data.commentPk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchFeedbackComments", data.articlePk);
        })
        .catch((err) => console.log(err));
    },

    // info article list
    fetchInfoArticles({ commit }, page) {
      http
        .get("/board/paging/info?page=" + page)
        .then(({ data }) => {
          commit("SET_ARTICLES", data.content);
          commit("SET_TOTAL_ELEMENTS", data.totalElements);
        })
        .catch((err) => console.log(err));
    },
  },
};

export default board;
