import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
// 引入自动导入
import AutoImport from "unplugin-auto-import/vite";
// 自动导入UI组件
import Components from "unplugin-vue-components/vite";
// gzip压缩
import viteCompression from "vite-plugin-compression";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      // 不在需要导入ref，reactive等
      imports: ["vue", "vue-router"],
      // 存放的位置
      dts: "src/auto-import.d.ts",
    }),
    Components({
      // 引入组件的信息存放位置
      dts: "src/components.d.ts",
    }),
    viteCompression(),
  ],
  resolve: {
    alias: {
      "@": resolve(__dirname, "./src"),
    },
  },
  server: {
    port: 3000,
    proxy: {
      "/api": {
        changeOrigin: true,
        target: "https://os.mnzdna.xyz",
        // target: "http://localhost:8434",
      },
    },
  },
});
