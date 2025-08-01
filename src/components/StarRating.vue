<template>
  <div class="star-rating">
    <span v-for="star in 5" :key="star" class="star" :class="{ 'filled': star <= modelValue }" @click="$emit('update:modelValue', star)">
      ★
    </span>
  </div>
</template>

<script setup>
defineProps({
  modelValue: { type: Number, required: true, default: 0 }
});
defineEmits(['update:modelValue']);
</script>

<style scoped>
.star { cursor: pointer; color: #ccc; font-size: 2.0rem; }
.star.filled { color: #000000; }
</style>
