export default {
  /**
   * 根据关键词分类
   * @param {*} data 数据集合
   * @param {*} typeList 分类集合
   * @returns
   */
  analysisType(data, typeList) {
    for (let e of typeList) {
      if (e.typeKeyword != null && e.typeKeyword != "") {
        let keywordList = e.typeKeyword.split(" ");
        for (let d of data) {
          for (let keyword of keywordList) {
            if (keyword !== "") {
              if (d.itemName.includes(keyword.trim())) {
                d.type = e.typeId;
              }
            }
          }
        }
      }
    }
    return data;
  }
};
