var dj;
(function () {
    // 全局方法，保存、获取数据，公共模块存放处
    dj = dj || {};
    var data = {};
    if (!window['dj']['setData']) {
        window['dj']['setData'] = function (obj) {
            data = $.extend({}, data, obj);
        };
    }
    if (!window['dj']['getData']) {
        window['dj']['getData'] = function (attr) {
            return attr ? data && data[attr] : data;
        };
    }
})();


$(function () {
    //树形
    var options = dj.getData('validate')[0];
    (function () {
        var funIds = $('#funIds');
        var setting = {
            view: {
                showIcon: false
            },
            check: {
                chkStyle: "radio",
                radioType: "all",
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };

        function zTreeOnCheck() {
            var ids = [];
            var selecteds = tree.getCheckedNodes(true);
            $.each(selecteds, function () {
                ids.push(this.id);
            })
            funIds.val(ids.join(','));
        };
        var tree = $.fn.zTree.init($(".fun-item .item-box"), setting, options.treeData);
        zTreeOnCheck();
    })()
});