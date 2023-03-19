<script lang="ts">
import { defineComponent, h, PropType } from "vue";

import { Pie } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
  CategoryScale,
  Plugin,
} from "chart.js";
import { left } from "@popperjs/core";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale);

export default defineComponent({
  name: "PieChart",
  components: {
    Pie,
  },
  props: {
    chartId: {
      type: String,
      default: "pie-chart",
    },
    width: {
      type: Number,
      default: 360,
    },
    height: {
      type: Number,
      default: 360,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object as PropType<Partial<CSSStyleDeclaration>>,
      default: () => {},
    },
    plugins: {
      type: Array as PropType<Plugin<"pie">[]>,
      default: () => [],
    },
  },
  setup(props) {
    let chartData = {};

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
    };

    return () =>
      h(Pie, {
        chartData,
        chartOptions,
        chartId: props.chartId,
        width: props.width,
        height: props.height,
        cssClasses: props.cssClasses,
        styles: props.styles,
        plugins: props.plugins,
      });
  },
});
</script>
