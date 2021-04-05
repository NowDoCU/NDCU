package com.hotsix.semochang.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * author: pinest94
 * since: 2021-04-02
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@ToString(exclude = {"founder", "commercial"})
public class Bookmark extends BaseTimeEntity{

    /***
     * 북마크 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /***
     * 창업자
     */
    @ManyToOne
    @JoinColumn(name = "founder_id")
    @JsonIgnore
    private Founder founder;

    /***
     * 상권
     */
    @ManyToOne
    @JoinColumn(name = "commercial_code")
    private Commercial commercial;

}
