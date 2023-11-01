<template>
    <span name="svg">
        <svg :style="styleObj">
            <use :fill="color" :xlink:href="href"></use>
        </svg>
    </span>
</template>

<script lang="ts" setup>
import {computed} from 'vue'
/* 使用该组件需要提供的属性有 name(svg文件的名字),必传. prefix,color,styleObj可传可不传 */

const props = defineProps({
  /* href属性的前缀,默认值 #icon- */
  prefix: {
    type: String,
    default: '#icon-',
  },
  /* svg图标的名字,对应icons目录下的svg文件的名字 */
  name: {
    type: String,
  },
  color: {
    type: String,
    default: 'blue',
  },
  styleObj: {
    type: Object,
    default: {
      width: '20px',
      height: '20px',
    },
  },
})

const href = computed(() => {
  return props['prefix'] + props['name']
})
</script>

<style lang="scss" scoped></style>
