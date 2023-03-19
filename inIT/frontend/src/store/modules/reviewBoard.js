import router from "../../router";
import http from "@/api/http.js";

const board = {
  state: {
    reviewArticles: [],
    reviewArticle: {},
    reviewComments: [],
  },
  mutations: {
    SET_REVIEW_ARTICLES: (state, reviewArticles) => (state.reviewArticles = reviewArticles),
    SET_REVIEW_ARTICLE: (state, reviewArticle) => (state.reviewArticle = reviewArticle),
    SET_REVIEW_COMMENTS: (state, reviewComments) => (state.reviewComments = reviewComments),
  },
  getters: {
    reviewArticles: (state) => state.reviewArticles,
    reviewArticle: (state) => state.reviewArticle,
    reviewComments: (state) => state.reviewComments,
    isReviewArticle: (state) => !!state.ReviewArticle,
  },
  actions: {
    // review article list
    fetchReviewArticles({ commit }) {
      http.get("/review/findAll")
        .then(({ data }) => {
          commit("SET_REVIEW_ARTICLES", data);
        })
        .catch((err) => console.log(err));
    },

    // review article CRUD
    createReviewArticle({ }, data) {
      http.post("/review/write", data)
        .then((res) => {
          router.push({ name: "ReviewArticleDetail", params: { articlePk: res.data } });
        })
        .catch((err) => console.log(err));
    },
    fetchReviewArticle({ commit, dispatch }, fetchInfo) {
      http.get(`/review/find/${fetchInfo.articlePk}/${fetchInfo.userPk}`)
        .then(({ data }) => {
          // data.createTime = data.createTime.substring(2, 10)
          commit("SET_REVIEW_ARTICLE", data);
          dispatch("fetchReviewComments", data.id);
        })
        .catch((err) => console.log(err));
    },
    editReviewArticle({ }, data) {
      console.log(data)
      http.put("/review/modify", data)
        .then((res) => {
          console.log(res)
          router.push({ name: "ReviewArticleDetail", params: { articlePk: data.id } });
        })
        .catch((err) => console.log(err));
    },
    deleteReviewArticle({ }, data) {
      http.delete(`/review/remove/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          router.push({name: 'ReviewArticleList'})
        })
        .catch((err) => console.log(err));
    },

    // review comments
    fetchReviewComments({ commit }, articlePk) {
      http.get("/reviewcomment/filterInfo/" + articlePk)
        .then(({ data }) => {
          data.forEach(data => {
            // data.createTime = data.createTime.substring(2, 10)
          })
          commit("SET_REVIEW_COMMENTS", data);
        })
        .catch((err) => console.log(err));
    },
    createReviewComment({ dispatch }, data) {
      http.post("/reviewcomment/write", data)
        .then((res) => {
          dispatch("fetchReviewComments", data.reviewId);
        })
        .catch((err) => console.log(err));
    },
    deleteReviewComment({ dispatch }, data){
      http.delete(`/reviewcomment/remove/${data.commentPk}/${data.userPk}`)
        .then((res) => {
          console.log(res)
          dispatch("fetchReviewComments", data.articlePk)
        })
        .catch((err) => console.log(err))
    },

    // review article like & unlike
    ReviewArticleLike({ dispatch }, data) {
      http.get(`/reviewlikes/recommend/${data.articlePk}/${data.userPk}`)
        .then((res) => {
          dispatch("fetchReviewArticle", data)
        })
        .catch((err) => console.log(err))
    },
    ReviewArticleUnlike({ dispatch }, data) {
      http.delete(`/reviewlikes/remove/${data.articlePk}/${data.userPk}`)
      .then((res) => {
          dispatch("fetchReviewArticle", data)
        })
        .catch((err) => console.log(err))
    },
  },
};

export default board;
