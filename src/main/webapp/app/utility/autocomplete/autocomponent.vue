 <template>
    <div>
      <div class="panel-footer" v-if="search_data.length">
        <ul class="list-group">
          <a href="#" class="list-group-item" v-for="data1 in search_data" @click="getName(data1.name)">{{ data1.name }}</a>
        </ul>
      </div>
    </div>
  </template>

<script>

import axios from 'axios';


  export default {
	name:'auto-comp',
    data(){
      return{
        query:'',
        search_data:[]
      }
    },
    methods:{
      getData:function(){
        this.search_data = [];
        var urlstr =  '/research-infrastructures/'+this.query
        axios.get(urlstr).then(response => {
        console.log(urlstr)  
        console.log(response.data)
        
        if (Array.isArray(response.data))
          this.search_data = response.data;

        
        });
      },
      getName:function(name){
        this.query = name;
        this.search_data = [];
      }
    }
  };

 
</script>