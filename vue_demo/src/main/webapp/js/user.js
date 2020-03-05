var vue = new Vue({
    el: "#app",
    data: {
        user: {id:"",username:"",password:"",age:"",sex:"",email:""},
        userList: []
    },


    methods: {
        findAll: function () {
            //在当前方法中定义一个变量，表明是vue对象
            var _this = this;
            axios.get("user/findAll.do")
                .then(function (response) {
                    _this.userList = response.data;
            })
                .catch(function (err) {
                    console.log(err);
            });
        },
        findById: function (userid) {
            var _this = this;
            axios.get("user/findById.do", {
                params: {
                    id: userid
                }
            }).then(function (response) {
              _this.user = response.data;
                $('#myModal').modal("show");
            }).catch(function (err) {
            });

        },
        update: function (user) {
            var _this = this;
            axios.post("user/updateUser.do",_this.user).then(function (response) {
                _this.findAll();
            }).catch(function (err) {
            });
        }
    },


    created:function(){//当页面加载的时候触发
        this.findAll();
    }
});