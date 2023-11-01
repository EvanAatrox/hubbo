import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path'
import {resolve} from 'path'
import {createSvgIconsPlugin} from 'vite-plugin-svg-icons'

export default defineConfig({
    plugins: [
        vue(),
        createSvgIconsPlugin({
            iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
            symbolId: 'icon-[dir]-[name]',
        }),
    ],
    resolve: {
        alias: [
            {
                find: '@',
                replacement: resolve(__dirname, './src'),
            },
        ],
    },
    server: {
        port: 80,
    },
    css: {
        preprocessorOptions: {
            scss: {
                javascriptEnabled: true,
                additionalData: '@import "./src/styles/variables.scss";',
            },
        },
    },
})
