import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  base: '/Poker-Probability-Calculator/', // IMPORTANT
  build: { outDir: 'docs' },     
  server: {
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
})
