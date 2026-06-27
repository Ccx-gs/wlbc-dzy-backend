/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          50:  '#f4f6f8',
          100: '#e4e8ed',
          200: '#c8cfd8',
          300: '#a1acba',
          400: '#738195',
          500: '#58677c',
          600: '#4a5668',
          700: '#3f4857',
          800: '#373e4a',
          900: '#30353f'
        },
        accent: {
          light: '#f0ebe4',
          DEFAULT: '#b8956a',
          deep: '#8b6d4a'
        }
      }
    }
  },
  plugins: []
}
