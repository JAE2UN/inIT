<template>
  <el-button
    @click="dialogFormVisible = true"
    :icon="EditPen"
    size="small"
    class="img-editbtn"
    >수정하기</el-button
  >

  <el-dialog
    v-model="dialogFormVisible"
    title="Edit Img"
    class="edit-img-dialog"
    width="50%"
  >
    <label>
      <input type="file" id="file" @change="handleFileUpload($event)" />
    </label>
    <el-button @click="submitFile()">Submit</el-button>
  </el-dialog>
</template>
<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import axios from "axios";
import http from "../../api/http";
import { DataBoard } from "@element-plus/icons-vue";

const store = useStore();
const router = useRouter();

const dialogFormVisible = ref(false);

let user = JSON.parse(sessionStorage.getItem("User"));

let fileData = "";
let response = ref("");

const submitFile = () => {
  const requestURL1 = "/s3/file/" + user.id + "?url=" + user.imgUrl; //삭제
  const requestURL2 = "/s3/file/" + user.id; // 업로드

  if (user.imgUrl != null) {
    http
      .delete(requestURL1)
      .then(() => {
        console.log("DELETE SUCCESS!!");
      })
      .catch((err) => {
        console.log("DELETE FAILURE!!");
        console.log(err);
      });
  }

  let formData = new FormData();
  formData.append("file", fileData);
  console.log(">> formData >> ", formData);

  http
    .post(requestURL2, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((data) => {
      console.log("UPLOAD SUCCESS!!");
      user.imgUrl = data;
      store.dispatch("updateUserInfo", user);
      router.go();
    })
    .catch((err) => {
      console.log("UPLOAD FAILURE!!");
      console.log(err);
    });
};

const handleFileUpload = () => {
  let file = document.getElementById("file");
  console.log(file.files);
  fileData = file.files[0];
  console.log(">>>> 1st element in files array >>>> ", fileData);
};
</script>
