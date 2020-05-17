/**
 * 右击菜单组件
 */
<template>
  <div
    class="right-menu"
    :style="'left:' + rightMenuLeft + 'px; top:' + rightMenuTop + 'px;'"
    v-if="rightMenuVisible"
    v-clickoutside="closeRightMenu"
  >
    <ul>
      <li>
        <button @click="cut" class="clearfix">
          剪切(T)
          <span class="fr">Ctrl+X</span>
        </button>
      </li>
      <div class="right-menu-line"></div>
      <li>
        <button @click="copy" class="clearfix">
          复制(C)
          <span class="fr">Ctrl+C</span>
        </button>
      </li>
      <div class="right-menu-line"></div>
      <li>
        <button @click="paste" class="clearfix">
          粘贴(P)
          <span class="fr">Ctrl+V</span>
        </button>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Clickoutside from 'element-ui/src/utils/clickoutside'
import { setRightMenuVisible } from '@/store/actions'
import store from '@/store'

export default {
  name: 'rightMenu',
  data () {
    return {

    }
  },
  directives: {
    Clickoutside
  },
  computed: {
    ...mapGetters([
      'rightMenuVisible',
      'rightMenuLeft',
      'rightMenuTop',
    ]),
  },
  watch: {
    rightMenuVisible (newVal, oldVal) {
      if (newVal) {

      }
    }
  },
  methods: {
    cut () {
      document.execCommand('cut')
    },
    copy () {
      document.execCommand('copy')
    },
    paste () {
      console.log('粘贴', window.clipboardData)
    },
    /**
     * 关闭右键菜单
     */
    closeRightMenu () {
      // this.$store.commit('chgRightMenuVisible', false)
      setChgRightMenuVisible(store, false)
    }
  }
};
</script>

<style lang="less">
.right-menu {
  position: fixed;
  z-index: 999999999;
  background: #fff;
  width: 200px;
  border: 1px solid #aaa;
  -webkit-box-shadow: 0 0 10px 10px #ccc;
  box-shadow: 2px 2px 2px 0 #555;
  .right-menu-line {
    width: 100%;
    height: 1px;
    background: #e5e5e5;
  }
  ul li {
    margin: 4px 0;
    &:hover {
      background: #e5e5e5;
      cursor: pointer;
    }
    button {
      padding: 2px 20px 2px 30px;
      background: transparent;
      border: none;
      width: 100%;
      height: 100%;
      outline: none;
      text-align: left;
      font-size: 12px;
    }
  }
}
</style>

