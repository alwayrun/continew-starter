/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.starter.extension.crud.model.resp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页信息
 *
 * @param <L> 列表数据类型
 * @author Charles7c
 * @since 1.0.0
 */
@Schema(description = "分页信息")
public class PageResp<L> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 列表数据
     */
    @Schema(description = "列表数据")
    private List<L> list;

    /**
     * 总记录数
     */
    @Schema(description = "总记录数", example = "10")
    private long total;

    /**
     * 基于 MyBatis Plus 分页数据构建分页信息，并将源数据转换为指定类型数据
     *
     * @param page        MyBatis Plus 分页数据
     * @param targetClass 目标类型 Class 对象
     * @param <T>         源列表数据类型
     * @param <L>         目标列表数据类型
     * @return 分页信息
     */
    public static <T, L> PageResp<L> build(Page<T> page, Class<L> targetClass) {
        if (null == page) {
            return empty();
        }
        PageResp<L> pageResp = new PageResp<>();
        pageResp.setList(BeanUtil.copyToList(page.getRecords(), targetClass));
        pageResp.setTotal(page.getTotalRow());
        return pageResp;
    }

    /**
     * 基于 MyBatis Plus 分页数据构建分页信息
     *
     * @param page MyBatis Plus 分页数据
     * @param <L>  列表数据类型
     * @return 分页信息
     */
    public static <L> PageResp<L> build(Page<L> page) {
        if (null == page) {
            return empty();
        }
        PageResp<L> pageResp = new PageResp<>();
        pageResp.setList(page.getRecords());
        pageResp.setTotal(page.getTotalRow());
        return pageResp;
    }

    /**
     * 基于列表数据构建分页信息
     *
     * @param page 页码
     * @param size 每页条数
     * @param list 列表数据
     * @param <L>  列表数据类型
     * @return 分页信息
     */
    public static <L> PageResp<L> build(int page, int size, List<L> list) {
        if (CollUtil.isEmpty(list)) {
            return empty();
        }
        PageResp<L> pageResp = new PageResp<>();
        pageResp.setTotal(list.size());
        // 对列表数据进行分页
        int fromIndex = (page - 1) * size;
        int toIndex = page * size + fromIndex;
        if (fromIndex > list.size()) {
            pageResp.setList(new ArrayList<>(0));
        } else if (toIndex >= list.size()) {
            pageResp.setList(list.subList(fromIndex, list.size()));
        } else {
            pageResp.setList(list.subList(fromIndex, toIndex));
        }
        return pageResp;
    }

    /**
     * 空分页信息
     *
     * @param <L> 列表数据类型
     * @return 分页信息
     */
    private static <L> PageResp<L> empty() {
        PageResp<L> pageResp = new PageResp<>();
        pageResp.setList(Collections.emptyList());
        return pageResp;
    }

    public List<L> getList() {
        return list;
    }

    public void setList(List<L> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}