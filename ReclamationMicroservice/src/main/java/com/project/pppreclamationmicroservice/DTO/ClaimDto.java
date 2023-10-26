package com.project.pppreclamationmicroservice.DTO;

import com.project.pppreclamationmicroservice.Entity.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimDto {
    Status status;
}
