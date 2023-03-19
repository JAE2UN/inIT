<template>
  <div class="stopwatch">
    <div id="clock">
      {{ minuite }} : {{ secView }}
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      timerCount: 120,
    };
  },
  computed: {
    minuite() {
      return parseInt(this.timerCount / 60)
    },
    sec() {
      return (this.timerCount % (this.minuite * 60))
    },
    secView() {
      if (this.minuite > 0) {
        return String(this.sec).padStart(2, "0")
      } else {
        return String(this.timerCount).padStart(2, "0")
      }
    },
  },
  watch: {
    timerCount: {
      handler(value) {
        if (value > 0) {
          setTimeout(() => {
            this.timerCount--;
          }, 1000);
        }
      },
      immediate: true,
    },
    autoStop: {
      
    }
  },
};
</script>
